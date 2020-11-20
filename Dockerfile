FROM maven:3.6.3-jdk-8 AS build-env
WORKDIR /app

# Copy pom and get dependencies as seperate layers
COPY pom.xml ./
RUN mvn dependency:resolve

# Copy everything else and build
COPY . ./
RUN mvn package -DfinalName=app

# Build runtime image
FROM openjdk:8-jre-alpine
EXPOSE 80
WORKDIR /app
COPY --from=build-env /app/target/app.jar ./app.jar
ENV SPRING_CONFIG_NAME=docker-application
# The environment variable used by spring to reference the external file
CMD ["/usr/bin/java", "-jar", "/app/app.jar"]
