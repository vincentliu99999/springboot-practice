package com.vincent.practice.data;

import static org.junit.Assert.assertNotNull;
import java.net.URI;
import java.text.ParseException;
import com.vincent.practice.data.rule.LocalDbCreationRule;
import com.vincent.practice.model.Todo;
import com.vincent.practice.repository.TodoRepository;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

@ExtendWith(LocalDbCreationRule.class) // SpringExtension.class MockitoExtension.class
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @ActiveProfiles("local")
// @TestPropertySource(properties = { "amazon.dynamodb.endpoint=http://localhost:8000/", "amazon.aws.accesskey=test1", "amazon.aws.secretkey=test231" })
public class TodoRepositoryTest {
    // @ClassRule // ClassRule -> ExtendWith
    public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule();

    // private DynamoDBMapper dynamoDBMapper;

    // @Autowired
    // private AmazonDynamoDB amazonDynamoDB;

    // @Autowired
    private DynamoDbClient ddb = buildClient();

    @Autowired
    TodoRepository repository;

    @BeforeAll
    static void initAll() {
      System.out.println("initAll");
    }

    private static DynamoDbClient buildClient() {
      final AwsSessionCredentials awsCreds = AwsSessionCredentials
          .create("access_key_id", "secret_key_id", "session_token");
      return DynamoDbClient
          .builder()
          .endpointOverride(URI.create("http://localhost:8000"))
          .region(Region.US_WEST_2)
          .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
          // .overrideConfiguration(
          //     ClientOverrideConfiguration.builder().apiCallTimeout(Duration.ofSeconds(1)).build())
          .build();
    }

    // https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-dynamodb-tables.html
    private static void createTable(final DynamoDbClient dbClient) {
      final String tableName = "Todo";
      final String partitionKeyName = "pk";
      final String RangeKeyName = "sk";
      final CreateTableRequest createTableRequest = CreateTableRequest.builder()
          .attributeDefinitions(
            AttributeDefinition.builder()
              .attributeName(partitionKeyName)
              .attributeType(ScalarAttributeType.S)
              .build(),
            AttributeDefinition.builder()
              .attributeName(RangeKeyName)
              .attributeType(ScalarAttributeType.S)
              .build())
          .keySchema(
            KeySchemaElement.builder()
              .attributeName(partitionKeyName)
              .keyType(KeyType.HASH)
              .build(),
            KeySchemaElement.builder()
              .attributeName(RangeKeyName)
              .keyType(KeyType.RANGE)
              .build())
          .provisionedThroughput(
              ProvisionedThroughput.builder()
              .readCapacityUnits(10L)
              .writeCapacityUnits(5L)
              .build())
          .tableName(tableName)
          .build();
  
      dbClient.createTable(createTableRequest);
    }

    @Test
    public void testSyncClient() {
      final DynamoDbClient dbClient = buildClient();
      assertNotNull(this.ddb);
      createTable(this.ddb);
      // createTable(dbClient, "table-" + ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
  
    }

    // TestEngine with ID 'junit-jupiter' failed to execute tests
    // java.lang.NoClassDefFoundError: org/junit/platform/commons/util/ClassNamePatternFilterUtils
    // https://github.com/junit-team/junit5/issues/1773
    
    
    @Test
    public void saveItem() throws IllegalAccessException, InstantiationException, ClassNotFoundException, DDBModelException, NOKeyException, ParseException {
      // TODO repository is null
      repository = new TodoRepository();
      // TODO software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException: Cannot do operations on a non-existent table (Service: DynamoDb, Status Code: 400, Request ID: b591620b-7acc-4acf-93c4-3460842c8b76, Extended Request ID: null)
      createTable(this.ddb); 
      repository.setDynamoDbClient(this.ddb);

      Todo todo = new Todo();
      todo.setPk("pk");
      todo.setSk("sk");
      repository.saveItem(todo);
      repository.saveTodoItem(todo);

      Todo result = repository.getTodoItemByPkBySk("pk", "sk");
      assertNotNull(result);

      System.out.println(result.getPk());
      // assertThat(result.size(), is(greaterThan(0)));
      // assertThat(result.get(0).getCost(), is(equalTo(EXPECTED_COST)));
    }
}
