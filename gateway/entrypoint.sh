#!/bin/sh
set -e

exec /gateway-1.0.0-SNAPSHOT-runner -Dquarkus.http.port=${GATEWAY_PORT}
