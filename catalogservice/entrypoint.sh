#!/bin/sh
set -e

exec /category_service-1.0.0-SNAPSHOT-runner -Dquarkus.http.port=${CATEGORY_PORT}
