#!/usr/bin/env bash
# shellcheck disable=SC2086
SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" > /dev/null 2>&1 && pwd -P)

iface="${iface:-enp0s8}"


wait4x_image="${wait4x_image:-atkrad/wait4x:2.14.2}"
mysql5_image="${mysql5_image:-mysql:5.7.41}"


pushd "${SCRIPT_DIR}"/../horizontal-sub-demo/doc || exit 1

docker run \
--network host \
--rm \
--name='wait4x' \
${wait4x_image} mysql root:666666@tcp\(127.0.0.1:3306\)/ --interval 1s --timeout 3600s && \
docker run \
-it \
--rm \
--network host \
-v "$(pwd)":/vagrant \
${mysql5_image} mysql --host 127.0.0.1 --user root --password=666666 --loose-default-character-set=utf8 -e "source /vagrant/database_table.sql;"

popd || exit 1


pushd "${SCRIPT_DIR}"/../vertical-sub-demo/doc || exit 1

docker run \
--network host \
--rm \
--name='wait4x' \
${wait4x_image} mysql root:666666@tcp\(127.0.0.1:3306\)/ --interval 1s --timeout 3600s && \
docker run \
-it \
--rm \
--network host \
-v "$(pwd)":/vagrant \
${mysql5_image} mysql --host 127.0.0.1 --user root --password=666666 --loose-default-character-set=utf8 -e "source /vagrant/init_sql.sql;"

popd || exit 1




