# controller

```log
Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2021-07-07 23:44:30,985 ERROR [main] org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter: 

***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of method restTemplate in com.vincent.practice.PracticeApplication required a bean of type 'org.springframework.boot.web.client.RestTemplateBuilder' that could not be found.
2

Action:

Consider defining a bean of type 'org.springframework.boot.web.client.RestTemplateBuilder' in your configuration.

2021-07-07 23:44:30,993 ERROR [main] org.springframework.test.context.TestContextManager: Caught exception while allowing TestExecutionListener [org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener@4a668b6e] to prepare test instance [com.vincent.practice.data.TodoListControllerV2Test@11d4dbd6]
java.lang.IllegalStateException: Failed to load ApplicationContext
```