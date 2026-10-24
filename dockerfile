FROM maven:3.8.3-openjdk-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/FileSqueezer.jar
RUN mkdir p /app/result
CMD ["java","-jar","FileSqueezer.jar"]
