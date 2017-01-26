#!/usr/bin/env bash
gradle clean build
docker build . -t mizan/res-client
docker run -p8080:8080 mizan/res-client