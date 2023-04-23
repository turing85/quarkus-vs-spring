#!/usr/bin/env bash
set -e

docker stop postgres
docker stop animals
docker network rm perf