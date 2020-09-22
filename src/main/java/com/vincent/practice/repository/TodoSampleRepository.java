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

}
