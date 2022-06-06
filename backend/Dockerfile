FROM maven:3.6.3-openjdk-11
ADD src src
ADD pom.xml .
ADD src/etc .
COPY target/classes/openapi.json .
RUN mvn package
EXPOSE 8000
ENTRYPOINT ["java","-jar","target/superscoring.jar"]
