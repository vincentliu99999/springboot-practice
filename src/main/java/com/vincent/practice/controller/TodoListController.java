package com.vincent.practice.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import com.vincent.practice.model.Todo;
import com.vincent.practice.repository.TodoRepository;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughputDescription;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.TableDescription;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

@RestController
@RequestMapping(value = "/todo")
public class TodoListController {

  private static final String TEMPLATE = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @Autowired
  private DynamoDbClient ddb;

  public DynamoDbClient getDynamoDbClient() {
    return ddb;
  }

  @Autowired
  private TodoRepository todoRepository;

  @GetMapping("/listTables")
  public List<String> listTables() {

    boolean moreTables = true;
    String lastName = null;
    List<String> allTables = new ArrayList<String>();

    while (moreTables) {
      try {
        ListTablesResponse response = null;
        if (lastName == null) {
          ListTablesRequest request = ListTablesRequest.builder().build();
          response = ddb.listTables(request);
        } else {
          ListTablesRequest request =
              ListTablesRequest.builder().exclusiveStartTableName(lastName).build();
          response = ddb.listTables(request);
        }

        List<String> tableNames = response.tableNames();

        if (tableNames.size() > 0) {
          for (String curName : tableNames) {
            allTables.add(curName);
            System.out.format("* %s\n", curName);
          }
        } else {
          System.out.println("No tables found!");
          System.exit(0);
        }

        lastName = response.lastEvaluatedTableName();
        if (lastName == null) {
          moreTables = false;
        }
      } catch (DynamoDbException e) {
        System.err.println(e.getMessage());
        System.exit(1);
      }

    }
    return allTables;
  }

  @GetMapping("/describeTable")
  public String describeTable(
      @RequestParam(value = "name", defaultValue = "Todo") String tableName) {

    DescribeTableRequest request = DescribeTableRequest.builder().tableName(tableName).build();

    try {
      TableDescription tableInfo = ddb.describeTable(request).table();

      if (tableInfo != null) {
        System.out.format("Table name  : %s\n", tableInfo.tableName());
        System.out.format("Table ARN   : %s\n", tableInfo.tableArn());
        System.out.format("Status      : %s\n", tableInfo.tableStatus());
        System.out.format("Item count  : %d\n", tableInfo.itemCount().longValue());
        System.out.format("Size (bytes): %d\n", tableInfo.tableSizeBytes().longValue());

        ProvisionedThroughputDescription throughputInfo = tableInfo.provisionedThroughput();
        System.out.println("Throughput");
        System.out.format("  Read Capacity : %d\n", throughputInfo.readCapacityUnits().longValue());
        System.out.format("  Write Capacity: %d\n",
            throughputInfo.writeCapacityUnits().longValue());

        List<AttributeDefinition> attributes = tableInfo.attributeDefinitions();
        System.out.println("Attributes");

        for (AttributeDefinition a : attributes) {
          System.out.format("  %s (%s)\n", a.attributeName(), a.attributeType());
        }
      }
    } catch (DynamoDbException e) {
      System.err.println(e.getMessage());
      return e.getMessage();
    }
    // snippet-end:[dynamodb.java2.describe_table.main]
    System.out.println("\nDone!");
    return "describeTable";
  }

  @PostMapping("/addTodo")
  public String addTodo(@RequestParam(value = "pk", defaultValue = "pk") String pk,
      @RequestParam(value = "sk", defaultValue = "sk") String sk) {
    String tableName = "Todo";

    HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();

    // Add all content to the table
    itemValues.put("pk", AttributeValue.builder().s(pk).build());
    itemValues.put("sk", AttributeValue.builder().s(sk).build());
    itemValues.put("todo", AttributeValue.builder().s("todo2").build());

    // Create a PutItemRequest object
    PutItemRequest request = PutItemRequest.builder().tableName(tableName).item(itemValues).build();

    try {
      ddb.putItem(request);
      System.out.println(tableName + " was successfully updated");

    } catch (ResourceNotFoundException e) {
      System.err.format("Error: The table \"%s\" can't be found.\n", tableName);
      System.err.println("Be sure that it exists and that you've typed its name correctly!");
      return e.getMessage();
    } catch (DynamoDbException e) {
      System.err.println(e.getMessage());
      return e.getMessage();
    }

    return "xxx";
  }

  @PostMapping("/updateTodo")
  public String updateTodo(@RequestParam(value = "pk", defaultValue = "pk") String pk,
      @RequestParam(value = "sk", defaultValue = "sk") String sk) {
    String tableName = "Todo";

    HashMap<String, AttributeValue> itemKey = new HashMap<String, AttributeValue>();

    itemKey.put("pk", AttributeValue.builder().s(pk).build());
    itemKey.put("sk", AttributeValue.builder().s(sk).build());

    HashMap<String, AttributeValueUpdate> updatedValues =
        new HashMap<String, AttributeValueUpdate>();

    // Update the column specified by name with updatedVal
    updatedValues.put("todo", AttributeValueUpdate.builder()
        .value(AttributeValue.builder().s("haha").build()).action(AttributeAction.PUT).build());

    UpdateItemRequest request = UpdateItemRequest.builder().tableName(tableName).key(itemKey)
        .attributeUpdates(updatedValues).build();

    try {
      ddb.updateItem(request);
    } catch (ResourceNotFoundException e) {
      System.err.println(e.getMessage());
      return e.getMessage();
    } catch (DynamoDbException e) {
      System.err.println(e.getMessage());
      return e.getMessage();
    }

    return "xxx";
  }

  @PostMapping("/testGet")
  public Todo testGet(@RequestParam(value = "pk2", defaultValue = "pk2") String pk,
      @RequestParam(value = "sk2", defaultValue = "sk2") String sk)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException,
      ClassNotFoundException, DDBModelException, NOKeyException, ParseException {
    return todoRepository.testGet(pk, sk);
  }

  @PostMapping("/testSave")
  public Todo testSave(@RequestParam(value = "pk", defaultValue = "pk") String pk,
      @RequestParam(value = "sk", defaultValue = "sk") String sk,
      @RequestParam(value = "todo", defaultValue = "todo") String desc)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException,
      ClassNotFoundException, DDBModelException, NOKeyException, ParseException {
    Todo todo = new Todo();
    todo.setPk(pk);
    todo.setSk(sk);
    todo.setTodo(desc);
    todo.setDesc(desc);
    return todoRepository.testSave(todo);
  }

}