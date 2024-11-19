#!/usr/bin/env bash
# shellcheck disable=SC2086


iface="${iface:-enp0s8}"


wait4x_image="${wait4x_image:-atkrad/wait4x:2.14.2}"
mysql5_image="${mysql5_image:-mysql:5.7.41}"
proxy="${proxy:-}"
workdir="${workdir:-$HOME/var/lib/mysqloo}"
while [ $# -gt 0 ]; do
    case "$1" in
        --iface|-i)
            iface="$2"
            shift
            ;;
        --proxy)
            proxy="$2"
            shift
            ;;
        --*)
            echo "Illegal option $1"
            ;;
    esac
    shift $(( $# > 0 ? 1 : 0 ))
done

ip4=$(/sbin/ip -o -4 addr list "${iface}" | awk '{print $4}' |cut -d/ -f1 | head -n1);



command_exists() {
  command -v "$@" > /dev/null 2>&1
}



fun_add_mynet(){
  docker network inspect mynet &>/dev/null || docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 --driver bridge mynet
}


fun_install_mysql_master(){


mkdir -p "${workdir}"/master/conf
mkdir -p "${workdir}"/master/data
cat > "${workdir}"/master/conf/mysql.conf.cnf <<EOF
[mysqld]

skip-host-cache
skip-name-resolve
lower_case_table_names =1
max_connections=4000
character_set_server = utf8mb4
collation_server = utf8mb4_unicode_ci
default_time_zone = '+08:00'

server-id=1
log_bin = /var/lib/mysql/mysql-bin.log
binlog_format = ROW
expire_logs_days =50
EOF

docker rm -f mysql_master 2>/dev/null || true
docker run -d \
--name mysql_master \
--restart always \
--network mynet \
-e MYSQL_ROOT_PASSWORD=666666 \
-v "${workdir}"/master/conf/mysql.conf.cnf:/etc/mysql/conf.d/mysql.conf.cnf \
-v "${workdir}"/master/data:/var/lib/mysql \
-p 3306:3306 ${mysql5_image}
}

fun_post_mysql_master(){
root_stmt='GRANT ALL PRIVILEGES ON *.* TO "root"@"%" IDENTIFIED BY "666666" WITH GRANT OPTION; FLUSH PRIVILEGES;'
docker run --net mynet --rm --name wait4x "${wait4x_image}" --timeout 300s mysql "root:666666@tcp(mysql_master:3306)/" && docker exec mysql_master sh -c "export MYSQL_PWD=666666; mysql -u root -e '$root_stmt'"
docker run --net mynet --rm --name wait4x "${wait4x_image}" --timeout 300s mysql "root:666666@tcp(mysql_master:3306)/" && docker exec mysql_master sh -c "export MYSQL_PWD=666666; mysql -u root -e 'show databases;'"
}



fun_install_mysql_slave(){


mkdir -p "${workdir}"/slave/conf
mkdir -p "${workdir}"/slave/data
cat > "${workdir}"/slave/conf/mysql.conf.cnf <<EOF
[mysqld]

skip-host-cache
skip-name-resolve
lower_case_table_names =1
max_connections=4000
character_set_server = utf8mb4
collation_server = utf8mb4_unicode_ci
default_time_zone = '+08:00'

server-id=2

EOF

docker rm -f mysql_slave 2>/dev/null || true
docker run -d \
--name mysql_slave \
--restart always \
--network mynet \
-e MYSQL_ROOT_PASSWORD=666666 \
-v "${workdir}"/slave/conf/mysql.conf.cnf:/etc/mysql/conf.d/mysql.conf.cnf \
-v "${workdir}"/slave/data:/var/lib/mysql \
-p 3307:3306 ${mysql5_image}
}

fun_post_mysql_slave(){
root_stmt='GRANT ALL PRIVILEGES ON *.* TO "root"@"%" IDENTIFIED BY "666666" WITH GRANT OPTION; FLUSH PRIVILEGES;'
docker run --net mynet --rm --name wait4x "${wait4x_image}" --timeout 300s mysql "root:666666@tcp(mysql_slave:3306)/" && docker exec mysql_slave sh -c "export MYSQL_PWD=666666; mysql -u root -e '$root_stmt'"
docker run --net mynet --rm --name wait4x "${wait4x_image}" --timeout 300s mysql "root:666666@tcp(mysql_slave:3306)/" && docker exec mysql_slave sh -c "export MYSQL_PWD=666666; mysql -u root -e 'show databases;'"
}



fun_setup_master(){

## CREATE USER mydb_slave_user IDENTIFIED BY 'mydb_slave_pwd';
## GRANT REPLICATION SLAVE ON *.* TO 'mydb_slave_user'@'%';
## FLUSH PRIVILEGES;

priv_stmt='CREATE USER mydb_slave_user IDENTIFIED BY "mydb_slave_pwd"; GRANT REPLICATION SLAVE ON *.* TO "mydb_slave_user"@"%" IDENTIFIED BY "mydb_slave_pwd"; FLUSH PRIVILEGES;'
docker run --net mynet --rm --name wait4x "${wait4x_image}" --timeout 300s mysql "root:666666@tcp(mysql_master:3306)/" && \
docker exec mysql_master sh -c "export MYSQL_PWD=666666; mysql -u root -e '$priv_stmt'"
}


fun_setup_slave(){

MS_STATUS=$(docker exec mysql_master sh -c 'export MYSQL_PWD=666666; mysql -u root -Bse "SHOW MASTER STATUS"')
CURRENT_LOG=$(echo "$MS_STATUS" | awk '{print $1}')
CURRENT_POS=$(echo "$MS_STATUS" | awk '{print $2}')


if [ "$CURRENT_LOG" = "" ];then
    echo "no current log"
    exit 1
fi

if [ "$CURRENT_POS" = "" ];then
    echo "no current pos"
    exit 1
fi

# echo "${MS_STATUS}";
# echo "${CURRENT_LOG}";
# echo "${CURRENT_POS}";

start_slave_stmt="CHANGE MASTER TO MASTER_HOST='mysql_master',MASTER_PORT=3306, MASTER_USER='mydb_slave_user',MASTER_PASSWORD='mydb_slave_pwd',MASTER_LOG_FILE='$CURRENT_LOG',MASTER_LOG_POS=$CURRENT_POS; START SLAVE;"
start_slave_cmd='export MYSQL_PWD=666666; mysql -u root -e "'
start_slave_cmd+="$start_slave_stmt"
start_slave_cmd+='"'

echo "${start_slave_stmt}";

docker run --net mynet --rm --name wait4x "${wait4x_image}" --timeout 300s mysql "root:666666@tcp(mysql_slave:3306)/" && docker exec mysql_slave sh -c "$start_slave_cmd" && \
docker exec mysql_slave sh -c "export MYSQL_PWD=666666; mysql -u root -e 'SHOW SLAVE STATUS \G'"



}




fun_add_mynet

echo "begin to install mysql master"
fun_install_mysql_master
fun_post_mysql_master

echo "begin to install mysql slave"
fun_install_mysql_slave
fun_post_mysql_slave

echo "begin to setup mysql master and slave"
fun_setup_master
fun_setup_slave
