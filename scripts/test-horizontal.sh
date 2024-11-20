#!/usr/bin/env bash


for i in {200..300};do
curl -X POST --url http://127.0.0.1:8080/course/add -d "id=${i}"
done

#curl -X GET --url "http://127.0.0.1:8080/course/del?id=321"
#curl -X POST -s --url "http://127.0.0.1:8080/course/add?id=321&createdAt=2022-06-11+00:00:01.456"



# curl -X GET -s --url http://127.0.0.1:8080/course/all
curl -X GET -s --url http://127.0.0.1:8080/course/page | jq
curl -X GET -s --url http://127.0.0.1:8080/course/page/jdbc | jq