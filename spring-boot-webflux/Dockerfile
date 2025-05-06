FROM openjdk:12-jdk-alpine

WORKDIR /app

COPY /target/spring-boot-webflux-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]






