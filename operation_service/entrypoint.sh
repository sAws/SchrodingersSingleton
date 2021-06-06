#!/bin/sh
set -e

exec /operation_service-1.0.0-SNAPSHOT-runner -Dquarkus.http.port=${OPERATION_PORT}
