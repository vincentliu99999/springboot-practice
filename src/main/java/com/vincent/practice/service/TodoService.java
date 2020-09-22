package com.vincent.practice.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import com.vincent.practice.model.Todo;
import com.vincent.practice.model.dao.PagedResult;
import com.vincent.practice.repository.TodoRepository;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
  @Autowired
  private TodoRepository todoRepository;

  public void batchSaveTodoItem(List<Todo> todos) throws Exception {
    todoRepository.batchSaveTodoItem(todos);
  }

  public Todo saveTodoItem(Todo todo) throws Exception {
    return todoRepository.saveItem(todo);
  }

  public List<Todo> queryTodoByPartitionKey(Todo todo)
      throws IllegalAccessException, InstantiationException, ClassNotFoundException, ParseException,
      IOException, DDBModelException, NOKeyException {
    return todoRepository.queryTodoByPartitionKey(todo);
  }

  public List<Todo> queryTodoByRangeKey(Todo todo)
      throws IllegalAccessException, InstantiationException, ClassNotFoundException, ParseException,
      IOException, DDBModelException, NOKeyException {
    return todoRepository.queryTodoByRangeKey(todo);
  }
}
