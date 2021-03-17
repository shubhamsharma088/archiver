FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY build/libs/archiver-0.0.1-SNAPSHOT.jar archiver.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar archiver.jar
