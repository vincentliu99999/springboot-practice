package com.vincent.practice.repository;

import java.text.ParseException;
import com.vincent.practice.model.Todo;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class TodoRepository extends DynamoCRUDRepository<Todo> {
  @Autowired
  private DynamoDbClient ddb;

  public Todo testGet(String pk, String sk)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException,
      ClassNotFoundException, DDBModelException, NOKeyException, ParseException {
    Todo todo = new Todo();
    todo.setPk(pk);
    todo.setSk(sk);

    return this.getItem(todo);
  }

  public Todo testSave(Todo todo)
      throws IllegalArgumentException, IllegalAccessException, DDBModelException, NOKeyException {
    return this.saveItem(todo);
  }
}