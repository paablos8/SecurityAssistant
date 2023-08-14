FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/SecurityAssistant-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "SecurityAssistant-0.0.1-SNAPSHOT.jar"]
