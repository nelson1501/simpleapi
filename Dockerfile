LABEL version="1.0"
LABEL description="Simple REST API image"
LABEL maintainer="Nelson"
ARG CODE_VERSION=latest
ENV TEMP_DIR=/tmp/docker
ENV WORK_DIR=/home/ubuntu

MAINTAINER Nelson
FROM ubuntu:${CODE_VERSION}
WORKDIR ${TEMP_DIR}
RUN apt-get -y update
RUN apt-get -y install openjdk-8-jdk
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
WORKDIR ${WORK_DIR}
COPY target/simpleapi.jar \$WORK_DIR
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=prod simpleapi.jar