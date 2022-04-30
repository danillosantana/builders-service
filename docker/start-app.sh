#!/bin/bash

#java -jar  -Dspring.profiles.active=$PROFILE -Dmysql.datasource.username=$MYSQL_USERNAME_PROD  -Dmysql.datasource.password=$MYSQL_PASSWORD_PROD sisget-service.jar
java -jar  -Dspring.profiles.active=prod -Dspring.datasource.username=root  -Dspring.datasource.password=root builders-service.jar