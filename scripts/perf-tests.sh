#!/usr/bin/env bash
set -e

cd "$( dirname "${BASH_SOURCE[0]}" )" 1> /dev/null

echo "Quarkus non-reactive, QUARKUS_DATASOURCE_JDBC_MAX_SIZE=20, OOB-experience"
../quarkus/scripts/perf-test.sh 20
../quarkus/scripts/cleanup.sh
echo "========================================"
echo "Quarkus non-reactive, QUARKUS_DATASOURCE_JDBC_MAX_SIZE=100"
../quarkus/scripts/perf-test.sh 100
../quarkus/scripts/cleanup.sh
echo "========================================"
echo "Quarkus, reactive, QUARKUS_DATASOURCE_REACTIVE_CACHE_PREPARED_STATEMENTS unset, QUARKUS_DATASOURCE_REACTIVE_MAX_SIZE=20"
../quarkus-reactive/scripts/perf-test-no-cache-prepared.sh 20
../quarkus-reactive/scripts/cleanup.sh
echo "========================================"
echo "Quarkus, reactive, QUARKUS_DATASOURCE_REACTIVE_CACHE_PREPARED_STATEMENTS unset, QUARKUS_DATASOURCE_REACTIVE_MAX_SIZE=100"
../quarkus-reactive/scripts/perf-test-no-cache-prepared.sh 100
../quarkus-reactive/scripts/cleanup.sh
echo "========================================"
echo "Quarkus, reactive, QUARKUS_DATASOURCE_REACTIVE_CACHE_PREPARED_STATEMENTS=true, QUARKUS_DATASOURCE_REACTIVE_MAX_SIZE=20"
../quarkus-reactive/scripts/perf-test-cache-prepared.sh 20
../quarkus-reactive/scripts/cleanup.sh
echo "========================================"
echo "Quarkus, reactive, QUARKUS_DATASOURCE_REACTIVE_CACHE_PREPARED_STATEMENTS=true, QUARKUS_DATASOURCE_REACTIVE_MAX_SIZE=100"
../quarkus-reactive/scripts/perf-test-cache-prepared.sh 100
../quarkus-reactive/scripts/cleanup.sh
