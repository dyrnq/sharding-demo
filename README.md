# sharding-demo

## boot vagrant

```bash
vagrant up shard
```



## boot mysql master slave

```bash
vagrant ssh shard
vagrant@shard:~$ cd /vagrant/scripts/
vagrant@shard:~/vagrant/scripts$./run-containers.sh
```

## init database

```bash
vagrant ssh shard
vagrant@shard:~$ cd /vagrant/scripts/
vagrant@shard:~/vagrant/scripts$./database-init.sh
```