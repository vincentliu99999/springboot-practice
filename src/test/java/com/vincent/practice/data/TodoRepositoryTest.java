package com.vincent.practice.data;

import java.text.ParseException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.vincent.practice.data.rule.LocalDbCreationRule;
import com.vincent.practice.model.Todo;
import com.vincent.practice.repository.TodoRepository;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(LocalDbCreationRule.class) // SpringExtension.class MockitoExtension.class
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@TestPropertySource(properties = { "amazon.dynamodb.endpoint=http://localhost:8000/", "amazon.aws.accesskey=test1", "amazon.aws.secretkey=test231" })
public class TodoRepositoryTest {
    // @ClassRule // ClassRule -> ExtendWith
    public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule();

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private DynamoDbClient ddb;

    @Autowired
    TodoRepository repository;

    private static final String EXPECTED_COST = "20";
    private static final String EXPECTED_PRICE = "50";
    @BeforeAll
    static void initAll() {
      System.out.println("initAll");
    }

    @BeforeEach // Before -> BeforeEach
    public void setup() throws Exception {

        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Todo.class);

            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

            amazonDynamoDB.createTable(tableRequest);
        } catch (ResourceInUseException e) {
            // Do nothing, table already created
        }

        // TODO How to handle different environments. i.e. AVOID deleting all entries in ProductInfo on table
        // dynamoDBMapper.batchDelete((List<ProductInfo>) repository.findAll());
    }

    // TestEngine with ID 'junit-jupiter' failed to execute tests
    // java.lang.NoClassDefFoundError: org/junit/platform/commons/util/ClassNamePatternFilterUtils
    // https://github.com/junit-team/junit5/issues/1773
    @Test
    public void saveItem() throws IllegalAccessException, InstantiationException, ClassNotFoundException, DDBModelException, NOKeyException, ParseException {

        Todo todo = new Todo();
        repository.saveItem(todo);
        // repository.saveTodoItem(todo);

        Todo result = repository.getTodoItemByPkBySk("pk", "sk");
        // assertThat(result.size(), is(greaterThan(0)));
        // assertThat(result.get(0).getCost(), is(equalTo(EXPECTED_COST)));
    }
}
