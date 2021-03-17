# Archiver

A simple microservice to accept multiple files as input and return zip file. The api avoids doing any Write intensive operation and process the input files directly into response zip file after some basic validations. This allow better performance as CPU is not waitng to get hold of disk access.

## How to run

- __Using docker__: Run `docker-compose up` in project root directory. It makes use of openjdk-8 running on top of light-weight alpine image.

- __Using gradle__: Run `./gradlew bootRun`

The application start up at 8080 by default. To change port [application.properties](src/main/resources/application.properties) or [docker-compose.yml](docker-compose.yml) for gradle and docker execution respectively.

## How to Test

Application has set of Unit tests case and Integration tests that are executed before every bootRun command. For testing via a rest client like Postman,

- Import the [postman collection](postman_collection.json)
- Pass files in field `files` with key type as `File`
