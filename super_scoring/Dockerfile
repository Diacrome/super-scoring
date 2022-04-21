FROM maven:3.6.3-openjdk-11
ADD src src
ADD pom.xml .
ADD src/etc/hibernate.properties .
ADD src/etc/service.properties .
ADD src/etc/service.properties.dev .
RUN mvn package
EXPOSE 8000
ENTRYPOINT ["java","-jar","target/super_scoring.jar"]