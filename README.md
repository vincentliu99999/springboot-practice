# Readme

[Spring Initializr](https://start.spring.io/)

```shell
curl localhost:8081/actuator/health
```

## SpringBoot with Maven

```shell
./mvnw install -f ./pom.xml
./mvnw clean -f ./pom.xml
```

### Run Application

```shell
# 1
./mvnw spring-boot:run

# 2
mvn spring-boot:run

# 3
./mvnw clean package
java -jar target/practice-0.0.1-SNAPSHOT.jar
jar tvf target/practice-0.0.1-SNAPSHOT.jar

# test
curl localhost:8080
```

### Note

- `@RestController` = `@Controller` + `@ResponseBody`
- `MappingJackson2HttpMessageConverter` convert Object to JSON
- @SpringBootApplication
  - @Configuration
  - @EnableAutoConfiguration
  - @ComponentScan

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

## DynamoCRUDRepository

method | request Object | response Object | Note
-------|----------------|----------------|----------------
`T getItem(T t)` | GetItemRequest | GetItemResponse | 取得單筆資料 by hash key, hash key + range key
`List<T> queryByPartitionKey(T t)` | QueryRequest | QueryResponse | 取得多筆資料 by hash key
`List<T> queryByRangeKey(T t)` | QueryRequest | QueryResponse | 取得多筆資料 by hash key + range key begins_with
`T saveItem(T t)` | PutItemRequest | PutItemResponse | 儲存單筆資料
`T updateItem(T t, String... updateFields)` | UpdateItemRequest | UpdateItemResponse | 指定欄位更新單筆資料
`<X extends Object> X counterAdd(X t, String... updateFields)` | UpdateItemRequest | UpdateItemResponse | TODO
`int deleteItem(T t)` | DeleteItemRequest | DeleteItemResponse | 刪除資料 by hash key, hash key + range key
`List<Map<String, AttributeValue>> batchGetPer100Item(String tableName, List<Map<String, AttributeValue>> keyItem)` | BatchGetItemRequest | BatchGetItemResponse | 批次取得多筆資料，一次最多 16 MB, 100 筆 item
`void batchWritePer25Item(String tableName, List<WriteRequest> keyItem)` | BatchWriteItemRequest | BatchWriteItemResponse | 批次寫入多筆資料
`PagedResult<T> pagingProcess(Builder builder, Integer pageSize, String cursor)` | QueryRequest | QueryResponse | 分頁取得多筆資料

## DynamoDB

```shell
cd /Users/vincent.liu/workspace/vincent/node/dynamodb_local_latest && java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb

dynamodb-admin

open http://localhost:8001
```

## Reference

- [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [Testing the Web Layer](https://spring.io/guides/gs/testing-web/)
- [How to run unit test with Maven &#8211; Mkyong.com](https://mkyong.com/maven/how-to-run-unit-test-with-maven/)
