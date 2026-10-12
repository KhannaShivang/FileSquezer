FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/FileCompressor-0.0.1-SNAPSHOT.jar app.jar
RUN mkdir -p /app/result
CMD ["java","-jar","app.jar"]
