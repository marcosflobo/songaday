# Dockerfile
FROM gradle:8.3.0-jdk17-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY . .
RUN gradle clean assemble

# actual container
FROM openjdk:17-oracle
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/*-all.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
