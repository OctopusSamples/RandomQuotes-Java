FROM maven:3.6.3-jdk-8 AS build-env
WORKDIR /app

# Copy pom and get dependencies as seperate layers
COPY pom.xml ./
RUN mvn dependency:resolve
RUN mvn dependency:tree

# Copy everything else and build
COPY . ./
RUN mvn package -DfinalName=app

# Update the package version
ENV VERSION=0.0.1
RUN mvn versions:set -DnewVersion=${VERSION}

# Build runtime image
FROM openjdk:8-jre-alpine
EXPOSE 80
WORKDIR /app
COPY --from=build-env /app/target/app.jar ./app.jar
# Use an external config file
COPY src/main/resources/docker-application.yml /app/docker-application.yml
COPY src/main/resources/postgres-application.yml /app/postgres-application.yml
ENV SPRING_CONFIG_NAME=docker-application
# The environment variable used by spring to reference the external file
CMD ["/usr/bin/java", "-jar", "/app/app.jar"]
