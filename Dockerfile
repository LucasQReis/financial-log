FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/financial-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.h2.console.settings.web-allow-others=true"]

