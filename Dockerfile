FROM openjdk:12-jdk-alpine
COPY final-project-backend-0.0.1-SNAPSHOT.jar final-project-backend-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "-Djdk.tls.client.protocols=TLSv1.2", "final-project-backend-0.0.1-SNAPSHOT.jar"]
