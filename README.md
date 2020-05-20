# Readme

[Spring Initializr](https://start.spring.io/)

## Scripts

```shell
./mvnw install -f ./pom.xml
./mvnw clean -f ./pom.xml
./mvnw spring-boot:run
curl localhost:8080
```

## Logs

```log
org.springframework.beans.factory.BeanDefinitionStoreException: Failed to parse configuration class [com.vincent.practice.PracticeApplication]; nested exception is org.springframework.context.annotation.ConflictingBeanDefinitionException: Annotation-specified bean name 'helloController' for bean class [com.vincent.practice.controller.HelloController] conflicts with existing, non-compatible bean definition of same name and class [com.vincent.practice.HelloController]
        at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:188) ~[spring-context-5.2.6.RELEASE.jar:5.2.6.RELEASE]
```

## Reference

- [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
