#!/usr/bin/env bash


curl -X POST -s --url http://127.0.0.1:8080/course/write -d "id=123"

curl -X GET -s --url http://127.0.0.1:8080/course/read