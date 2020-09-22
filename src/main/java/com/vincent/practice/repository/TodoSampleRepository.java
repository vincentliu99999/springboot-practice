package com.vincent.practice.repository;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import com.vincent.practice.model.Todo;
import com.vincent.practice.repository.ddbmapper.DDBMapper;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

/**
 * saveItem getItem queryByPartitionKey queryByRangeKey updateItem counterAdd deleteItem
 * batchGetPer100Item batchWritePer25Item pagingProcess
 */
@Repository
public class TodoSampleRepository extends DynamoCRUDRepository<Todo> {
  @Autowired
  private DynamoDbClient ddb;

  private static final String TABLE_NAME = "Todo";

  public Todo saveTodoItem(Todo todo)
      throws IllegalAccessException, DDBModelException, NOKeyException {
    return this.saveItem(todo);
  }

  public Todo getTodoByPk(String pk, String sk)
      throws IllegalAccessException, InstantiationException, ClassNotFoundException,
      DDBModelException, NOKeyException, ParseException {
    Todo todo = new Todo();
    todo.setPk(pk);
    todo.setSk(sk);

    return this.getItem(todo);

  }

  public void queryTodoByPartitionKey() {

  }

  public void queryTodoByRangeKey() {

  }

  public Todo updateTodoItem(Todo todo) throws IllegalAccessException, ClassNotFoundException,
      InstantiationException, DDBModelException, ParseException {
    HashMap<String, AttributeValue> keyAttributeValues = new HashMap<>();
    keyAttributeValues.put("pk", AttributeValue.builder().s(todo.getPk()).build());
    keyAttributeValues.put("sk", AttributeValue.builder().s(todo.getSk()).build());

    String updateExpression = "SET done = :done";

    HashMap<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(":done", AttributeValue.builder().bool(todo.isDone()).build());

    UpdateItemRequest request = UpdateItemRequest.builder().tableName(TABLE_NAME)
        .key(keyAttributeValues).updateExpression(updateExpression)
        .expressionAttributeValues(expressionAttributeValues).returnValues("ALL_NEW").build();

    Map<String, AttributeValue> responseMap = ddb.updateItem(request).attributes();

    Todo outputModel = new Todo();
    DDBMapper.populateEntity(outputModel, responseMap);

    return outputModel;
  }

  public void counterAddTodo() {

  }

  public void deleteTodoItem() {

  }

  public void batchGetPer100TodoItem() {

  }

  public void batchWritePer25TodoItem() {

  }

  public void todoPagingProcess() {

  }
}
