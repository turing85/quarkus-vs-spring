#!/usr/bin/env bash
set -e

event_loop_size="${1:-0}"

cd "$( dirname "${BASH_SOURCE[0]}" )" 1> /dev/null
cd ..

./mvnw clean package

# Build image
docker-compose -f local-deployment/docker-compose.yml build animal-service-temurin

docker network create perf

# Deploy environment
docker run \
  --rm \
  --detach \
  --name postgres \
  --env POSTGRES_USER=postgres \
  --env POSTGRES_PASSWORD=postgres \
  --env POSTGRES_DB=postgres \
  --env POSTGRES_MULTIPLE_DATABASES=animals \
  --cpus=1 \
  --volume ./local-deployment/postgres/docker-entrypoint-initdb.d:/docker-entrypoint-initdb.d/:ro \
  --cpuset-cpus=0 \
  --publish '5432:5432' \
  --network perf \
  postgres:15.2-alpine3.17 \
  postgres -c 'max_connections=500'

docker run \
  --rm \
  --detach \
  --name animals \
  --env QUARKUS_HTTP_HOST=0.0.0.0 \
  --env QUARKUS_DATASOURCE_REACTIVE_EVENT_LOOP_SIZE="${event_loop_size}" \
  --env QUARKUS_DATASOURCE_REACTIVE_MAX_SIZE=100 \
  --env DB_HOST=postgres \
  --env DB_PORT=5432 \
  --env DB_DATABASE=animals \
  --env DB_USER=animals \
  --env DB_PASSWORD=animals \
  --env JAVA_TOOL_OPTIONS="-Djava.util.logging.manager=org.jboss.logmanager.LogManager" \
  --cpus=2 \
  --memory=128M \
  --cpuset-cpus=1,2 \
  --publish '8080:8080' \
  --network perf \
  localhost/animal-service:quarkus-reactive-temurin

# Prepare database
./mvnw flyway:migrate

# Perf test
cd ../wrk/lua-scripts
taskset -c 3 \
  wrk \
    --threads 1 \
    --connections 100 \
    --duration 1m \
    --script create_animals.lua \
    http://localhost:8080

taskset -c 3 \
  wrk \
    --threads 1 \
    --connections 100 \
    --duration 1m \
    --script create_animals.lua \
    http://localhost:8080

taskset -c 3 \
  wrk \
    --threads 1 \
    --connections 100 \
    --duration 1m \
    --script create_animals.lua \
    http://localhost:8080