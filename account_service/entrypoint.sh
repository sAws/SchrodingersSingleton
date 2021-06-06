#!/bin/sh
set -e

exec /account_service-1.0.0-SNAPSHOT-runner -Dquarkus.http.port=${ACCOUNT_PORT}
