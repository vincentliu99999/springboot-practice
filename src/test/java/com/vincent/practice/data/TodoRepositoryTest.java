package com.vincent.practice.data;

import java.text.ParseException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.vincent.practice.PracticeApplication;
import com.vincent.practice.data.rule.LocalDbCreationRule;
import com.vincent.practice.model.Todo;
import com.vincent.practice.repository.TodoRepository;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith({MockitoExtension.class, LocalDbCreationRule.class})
@SpringBootTest(classes = PracticeApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = { 
  "amazon.dynamodb.endpoint=http://localhost:8000/", 
  "amazon.aws.accesskey=test1", 
  "amazon.aws.secretkey=test231" })
public class TodoRepositoryTest {
  public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule();
  
  private static DynamoDBMapper dynamoDBMapper;

  @Autowired
  private static AmazonDynamoDB amazonDynamoDB;

  @Autowired
  TodoRepository repository;

  private static final String EXPECTED_COST = "20";
  private static final String EXPECTED_PRICE = "50";

  @BeforeAll
  public static void setup() throws Exception {
    try {
      dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

      CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Todo.class);

      tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

      System.out.println("dynamoDB is null?" + dynamoDB == null);
      System.out.println("tableRequest is null?" + tableRequest == null);
      System.out.println("amazonDynamoDB is null?" + amazonDynamoDB == null);

      amazonDynamoDB.createTable(tableRequest);
  } catch (Exception e) {
      // Do nothing, table already created
      e.printStackTrace();
  }
      
      //...

      // dynamoDBMapper.batchDelete(
      //   (List<Todo>)repository.);
  }

  @Test
  public void givenItemWithExpectedCost_whenRunFindAll_thenItemIsFound() throws IllegalAccessException, InstantiationException, ClassNotFoundException, DDBModelException, NOKeyException, ParseException { 
      Todo saveTodo = new Todo();
      saveTodo.setPk("pk");
      saveTodo.setSk("sk");
      repository.saveItem(saveTodo);

      Todo todo = repository.getTodoItemByPkBySk("pk", "sk");

      System.out.println(todo.getPk());

  }
}
