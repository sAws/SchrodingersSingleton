#!/bin/sh
set -e

exec /pipeline_service-1.0.0-SNAPSHOT-runner -Dquarkus.http.port=${PIPELINE_PORT}
