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
# https://spring.io/guides/topicals/spring-boot-docker/
# docker build -t springboot/practice .
# docker run -p 8080:8080 springboot/practice
# FROM openjdk:8-jdk-alpine
# RUN addgroup -S spring && adduser -S vincent -G spring
# USER vincent:spring
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]

# mkdir -p target/dependency
# cd target/dependency
# jar -xf ../*.jar

FROM openjdk:8-jdk-alpine

RUN addgroup -S spring && adduser -S vincent -G spring
USER vincent:spring

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","com.vincent.practice.PracticeApplication"]
