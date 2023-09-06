FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar app.jar
COPY ./ ./
ENTRYPOINT ["java","-jar","/app.jar"]
