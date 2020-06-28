# Readme

[Spring Initializr](https://start.spring.io/)

## Scripts

```shell
# display project dependencies
mvn dependency:tree
mvn test
mvn -Dtest=HelloControllerTest test

./mvnw install -f ./pom.xml
./mvnw clean -f ./pom.xml
./mvnw package -f ./pom.xml
./mvnw spring-boot:run
curl localhost:8080

./mvnw package && java -jar target/practice-0.0.1-SNAPSHOT.jar
curl localhost:8080/hello
```

## Logs

```log
org.springframework.beans.factory.BeanDefinitionStoreException: Failed to parse configuration class [com.vincent.practice.PracticeApplication]; nested exception is org.springframework.context.annotation.ConflictingBeanDefinitionException: Annotation-specified bean name 'helloController' for bean class [com.vincent.practice.controller.HelloController] conflicts with existing, non-compatible bean definition of same name and class [com.vincent.practice.HelloController]
        at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:188) ~[spring-context-5.2.6.RELEASE.jar:5.2.6.RELEASE]
```

`mvn dependency:tree | grep junit`

```log
[INFO]    +- org.junit.jupiter:junit-jupiter:jar:5.6.2:test
[INFO]    |  +- org.junit.jupiter:junit-jupiter-api:jar:5.6.2:test
[INFO]    |  |  \- org.junit.platform:junit-platform-commons:jar:1.6.2:test
[INFO]    |  +- org.junit.jupiter:junit-jupiter-params:jar:5.6.2:test
[INFO]    |  \- org.junit.jupiter:junit-jupiter-engine:jar:5.6.2:test
[INFO]    |     \- org.junit.platform:junit-platform-engine:jar:1.6.2:test
[INFO]    +- org.mockito:mockito-junit-jupiter:jar:3.3.3:test
```

## Travis

[Job Lifecycle](https://docs.travis-ci.com/user/job-lifecycle/)

## Swagger

- [API Documentation &amp; Design Tools for Teams | Swagger](https://swagger.io/)
- [springdoc-openapi](https://springdoc.org/)
- [在 Spring Boot 项目中使用 Swagger 文档](https://www.ibm.com/developerworks/cn/java/j-using-swagger-in-a-spring-boot-project/index.html)

## Dockerfile

1. add Dockerfile

```shell
# image tage as springboot/practice
docker build -t springboot/practice .

docker run -p 8080:8080 springboot/practice
```

## Reference

- [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [Testing the Web Layer](https://spring.io/guides/gs/testing-web/)
- [How to run unit test with Maven &#8211; Mkyong.com](https://mkyong.com/maven/how-to-run-unit-test-with-maven/)
