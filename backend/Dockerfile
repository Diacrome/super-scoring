FROM maven:3.6.3-openjdk-11 AS build
ADD src src
ADD pom.xml .
RUN mvn clean package

FROM openjdk:11-jre-slim
COPY --from=build /target/superscoring.jar .
COPY src/etc/* .
EXPOSE 8000
ENTRYPOINT ["java","-jar","superscoring.jar"]
