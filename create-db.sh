#!/bin/bash

docker run -d --name dive-log-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=dive-log -p 5431:5432 postgres:14.2
