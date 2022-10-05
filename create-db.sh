#!/bin/bash

docker run -d --name dive-log-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=dive-log -p 5431:5432 postgres:12

export JDBC_DATABASE_URL=jdbc:postgresql://localhost:5431/dive-log?user=postgres&password=postgres&sslmode=disable