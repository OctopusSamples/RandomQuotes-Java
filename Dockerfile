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
WORKDIR /app
COPY --from=build-env /app/target/*.jar ./app.jar
CMD ["/usr/bin/java", "-jar", "/app/app.jar"]
EXPOSE 80