FROM octopusdeploy/randomquotesjavabuild AS build-env
WORKDIR /app

# Copy pom and get dependencies as seperate layers
COPY pom.xml ./
RUN mvn dependency:resolve

# Copy everything else and build
COPY . ./
RUN mvn package
RUN ls -la /app/target

# Build runtime image
FROM openjdk:8-jre-alpine
EXPOSE 80
WORKDIR /app
COPY --from=build-env /app/target/randomquotes.0.1.7.jar ./app.jar
# Use an external config file
COPY src/main/resources/docker-application.yml /app/docker-application.yml
# The environment variable used by spring to reference the external file
CMD ["/usr/bin/java", "-jar", "/app/app.jar", "--spring.config.location=file:///app/docker-application.yml"]
