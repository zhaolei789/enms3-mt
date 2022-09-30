#!/usr/bin bash

#mvn clean package -Dmaven.test.skip=true

#docker build -t ewsdsc/system .
docker build -t hub.c.163.com/xvpindex/ewsdsc/system .

docker push hub.c.163.com/xvpindex/ewsdsc/system

#docker run -p 9800:9800 -d ewsdsc/system
docker run -p 9800:9800 -d hub.c.163.com/xvpindex/ewsdsc/system
