#!/usr/bin/env bash
set -e

docker stop postgres 1> /dev/null
docker stop animals 1> /dev/null
docker network rm perf 1> /dev/null