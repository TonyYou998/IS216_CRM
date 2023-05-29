FROM openjdk:17-jdk-slim as build
MAINTAINER uit.com
copy target/crm-0.0.1-SNAPSHOT.jar   crm-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","crm-0.0.1-SNAPSHOT.jar"]