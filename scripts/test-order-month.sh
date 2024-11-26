#!/usr/bin/env bash

x=1;
for i in {200..220};do
current_time=$(date -d "now +$x month" +"%Y-%m-%d+%H:%M:%S.%3N")

# 输出结果
echo "当前时间: $current_time"
curl -sX POST \
--url "http://127.0.0.1:8080/test-order/add?createdAt=$current_time"
sleep 1
((x=x+1))
done
