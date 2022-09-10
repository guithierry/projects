FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /tmp
COPY pom.xml /tmp
COPY src /tmp/src
RUN mvn package -DskipTests

FROM openjdk:17
EXPOSE 8080
CMD java -jar /data/project.jar
COPY --from=build /tmp/target/project.jar /data/project.jar