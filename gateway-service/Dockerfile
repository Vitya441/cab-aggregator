FROM eclipse-temurin:21-jre
WORKDIR /workspace
COPY target/*.jar /workspace/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
