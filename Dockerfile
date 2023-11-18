# Dockerfile
FROM openjdk:17-oracle

WORKDIR /app

CMD ["./gradlew", "clean", "assemble"]

COPY build/libs/*-all.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
