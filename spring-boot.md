# Spring Boot

## Getting Started

- [Developing Your First Spring Boot Application](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-first-application)

### System Requirements

- Java 8 ~ 14
- Spring 5.2.7
- Maven 3.3+

## Using Spring Boot

### Build Systems

- [Maven](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-maven)

### Auto-configuration

- `@SpringBootApplication`, `@EnableAutoConfiguration` 只能在 `@Configuration` 擇一使用
- 排除 `@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})`

### Spring Beans and Dependency Injection

- `@ComponentScan` + `@Autowired`
- `@ComponentScan`: `@Component`, `@Service`, `@Repository`, `@Controller` 自動註冊為 Spring Beans
- 如果只有一個 constructor，可以省略 `@Autowired`

### Using the @SpringBootApplication Annotation

- `@SpringBootApplication` = `@EnableAutoConfiguration` + `@ComponentScan` + `@Configuration`

### Running Your Application

```shell
# Running as a Packaged Application
java -jar target/myapplication-0.0.1-SNAPSHOT.jar

# Using the Maven Plugin, environment variable by MAVEN_OPTS
mvn spring-boot:run
export MAVEN_OPTS=-Xmx1024m
```

### Developer Tools

`spring-boot-devtools`

#### Property Defaults

- 多數 library 會用 cache 增進效能
- 開發時可在 `application.properties` 指定屬性

### Packaging Your Application for Production

- 有需要可再加上 `spring-boot-actuator`
- Reference: <https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready>

## Spring Boot Features

### SpringApplication

`SpringApplication.run`

#### Startup Failure

跑不起來 `FailureAnalyzers` 會告訴你原因

#### Customizing SpringApplication

1. instance 建立時
1. `application.properties`

### Externalized Configuration

TODO

## Spring Boot Actuator: Production-ready Features

- <https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html>
- <https://www.javadevjournal.com/spring-boot/spring-boot-actuator/>
