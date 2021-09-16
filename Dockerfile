ARG VERSION=0.0.1

FROM maven:3.6.3-jdk-8 AS build-env
WORKDIR /app

# Copy pom and get dependencies as seperate layers
COPY pom.xml ./
RUN mvn --batch-mode dependency:resolve
RUN mvn --batch-mode dependency:tree --no-transfer-progress

# Copy everything else
COPY . ./

# Update the package version
RUN mvn versions:set -DnewVersion=$VERSION

# Now build
RUN mvn --batch-mode package -DfinalName=app

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
