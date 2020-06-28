# tag as springboot/practice
# docker build -t springboot/practice .
# docker run -d -p 8080:8080 --name springboot/practice
# docker run -d --name my-springboot2 -p 8080:8080 springboot/practice
# (3m 5s 987ms)
# FROM maven:3.6.3-openjdk-8 AS MAVEN_BUILD

# COPY pom.xml /build/
# COPY src /build/src/
# WORKDIR /build/
# RUN mvn package

# FROM openjdk:8-jre-alpine
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]

# https://spring.io/guides/gs/spring-boot-docker/
# docker build -t springboot/practice .
# docker run -p 8080:8080 springboot/practice
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]