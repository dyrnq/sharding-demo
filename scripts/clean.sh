#!/usr/bin/env bash

docker ps -a --format="{{.Names}}" | xargs -r -I{} docker rm -f {}

workdir="${workdir:-$HOME/var/lib/mysqloo}"

sudo rm -rf "${workdir}"