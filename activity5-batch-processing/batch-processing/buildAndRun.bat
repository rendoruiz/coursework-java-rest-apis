@echo off
call mvn clean package
call docker build -t ca.nait.dmit/dmit2015-assignment05-rendoruiz .
call docker rm -f dmit2015-assignment05-rendoruiz
call docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-assignment05-rendoruiz ca.nait.dmit/dmit2015-assignment05-rendoruiz