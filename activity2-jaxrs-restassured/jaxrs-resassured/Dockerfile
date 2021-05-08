# Wildfly settings
FROM jboss/wildfly:latest
ADD target/dmit2015-assignment02-rendoruiz.war /opt/jboss/wildfly/standalone/deployments/

USER root
RUN ln -s -f /usr/share/zoneinfo/MST /etc/localtime

