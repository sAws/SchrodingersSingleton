#!/bin/sh
set -e

exec /transfer_service-1.0.0-SNAPSHOT-runner -Dquarkus.http.port=${TRANSFER_PORT}
