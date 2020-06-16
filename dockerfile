ARG CODE_VERSION=latest
FROM ubuntu:${CODE_VERSION}
LABEL version="1.0"
LABEL description="Simple REST API image"
LABEL maintainer="Nelson"
WORKDIR /tmp/docker
RUN apt-get -y update
RUN apt-get -y install openjdk-8-jre
WORKDIR /home/ubuntu
ADD target/simpleapi.jar .
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=prod simpleapi.jar
