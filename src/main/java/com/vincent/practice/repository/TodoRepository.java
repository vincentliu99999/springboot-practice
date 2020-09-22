package com.vincent.practice.repository;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.vincent.practice.model.Todo;
import com.vincent.practice.model.dao.PagedResult;
import com.vincent.practice.repository.ddbmapper.DDBMapper;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.DDBTableMeta;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest.Builder;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

@Repository
public class TodoRepository extends DynamoCRUDRepository<Todo> {
  @Autowired
  private DynamoDbClient ddb;

  private static final String TABLE_NAME = "Todo";

  /**
   * save single item
   * 
   * @param todo
   * @return
   * @throws IllegalAccessException
   * @throws DDBModelException
   * @throws NOKeyException
   */
  public Todo saveTodoItem(Todo todo)
      throws IllegalAccessException, DDBModelException, NOKeyException {
    return this.saveItem(todo);
  }

  /**
   * batch save per 25 items
   * 
   * @param todos
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws DDBModelException
   * @throws NOKeyException
   */
  public void batchSaveTodoItem(List<Todo> todos)
      throws IllegalArgumentException, IllegalAccessException, DDBModelException, NOKeyException {
    List<WriteRequest> list = new ArrayList<WriteRequest>();

    for (Todo todo : todos) {
      DDBTableMeta meta = DDBMapper.extractEntityMeta(todo, DDBMapper.PUT_MODE);
      HashMap<String, AttributeValue> attributeMap = meta.getAttributeMap();

      list.add(WriteRequest.builder().putRequest(PutRequest.builder().item(attributeMap).build())
          .build());
    }
    this.batchWritePer25Item(TABLE_NAME, list);
  }

  /**
   * get item by sk
   * 
   * Exception: The provided key element does not match the schema
   */
  public Todo getTodoItemByPk(String pk) throws IllegalAccessException, InstantiationException,
      ClassNotFoundException, DDBModelException, NOKeyException, ParseException {
    Todo todo = new Todo();
    todo.setPk(pk);

    return this.getItem(todo);
  }

  /**
   * get item by pk, sk
   * 
   * @param pk
   * @param sk
   * @return
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws ClassNotFoundException
   * @throws DDBModelException
   * @throws NOKeyException
   * @throws ParseException
   */
  public Todo getTodoItemByPkBySk(String pk, String sk)
      throws IllegalAccessException, InstantiationException, ClassNotFoundException,
      DDBModelException, NOKeyException, ParseException {
    Todo todo = new Todo();
    todo.setPk(pk);
    todo.setSk(sk);

    return this.getItem(todo);
  }

  /**
   * query by pk
   * 
   * @param todo
   * @return
   * @throws IllegalAccessException
   * @throws ParseException
   * @throws InstantiationException
   * @throws DDBModelException
   * @throws ClassNotFoundException
   * @throws NOKeyException
   */
  public List<Todo> queryTodoByPartitionKey(Todo todo)
      throws IllegalAccessException, ParseException, InstantiationException, DDBModelException,
      ClassNotFoundException, NOKeyException {
    List<Todo> outputModel = queryByPartitionKey(todo);

    return outputModel;
  }

  /**
   * query by pk + sk begin with
   * 
   * @param todo
   * @return
   * @throws IllegalAccessException
   * @throws ParseException
   * @throws InstantiationException
   * @throws DDBModelException
   * @throws ClassNotFoundException
   * @throws NOKeyException
   */
  public List<Todo> queryTodoByRangeKey(Todo todo) throws IllegalAccessException, ParseException,
      InstantiationException, DDBModelException, ClassNotFoundException, NOKeyException {
    List<Todo> outputModel = queryByRangeKey(todo);
    return outputModel;
  }

  public PagedResult<Todo> getPaginatedTodoByPkBySkPrefix(String pk, String skPrefix, int pageSize,
      String cursor) throws IllegalAccessException, ParseException, IOException,
      InstantiationException, DDBModelException, ClassNotFoundException {

    HashMap<String, AttributeValue> attributeMap = new HashMap<>();
    attributeMap.put(":pk", AttributeValue.builder().s(pk).build());
    attributeMap.put(":sk", AttributeValue.builder().s(skPrefix).build());

    Builder builder = QueryRequest.builder().tableName(TABLE_NAME)
        .keyConditionExpression("pk = :pk and begins_with(sk, :sk)")
        .expressionAttributeValues(attributeMap).scanIndexForward(false);

    return pagingProcess(builder, pageSize, cursor);
  }

  public PagedResult<Todo> getPaginatedTodoByPk(String pk, int pageSize, String cursor)
      throws IllegalAccessException, ParseException, IOException, InstantiationException,
      DDBModelException, ClassNotFoundException {

    HashMap<String, AttributeValue> attributeMap = new HashMap<>();
    attributeMap.put(":pk", AttributeValue.builder().s(pk.toString()).build());
    attributeMap.put(":sk", AttributeValue.builder().s("pk").build());

    Builder builder = QueryRequest.builder().tableName(TABLE_NAME)
        .keyConditionExpression("pk = :pk and begins_with(sk, :sk)")
        .expressionAttributeValues(attributeMap).scanIndexForward(false);

    return pagingProcess(builder, pageSize, cursor);
  }

  public Todo updateDone(Todo todo) throws IllegalAccessException, ClassNotFoundException,
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

  public int deleteTodoItem(Todo todo)
      throws IllegalAccessException, DDBModelException, NOKeyException {
    return this.deleteItem(todo);
  }
}
