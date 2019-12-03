FROM maven:3.3-jdk-8 AS build-env
WORKDIR /app

# Copy pom and get dependencies as seperate layers
COPY pom.xml ./
RUN mvn dependency:resolve

# Copy everything else and build
COPY . ./
RUN mvn package

# Build runtime image
FROM openjdk:8-jre-alpine
EXPOSE 80
WORKDIR /app
COPY --from=build-env /app/target/*.jar ./app.jar
# Use an external config file
COPY src/main/resources/docker-application.yml /app/docker-application.yml
# The environment variable used by spring to reference the external file
ENV SPRING_CONFIG_LOCATION /app/docker-application.yml
CMD ["/usr/bin/java", "-jar", "/app/app.jar"]
