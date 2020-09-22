package com.vincent.practice.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.vincent.practice.model.Todo;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import com.vincent.practice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/todo/v2")
public class TodoListControllerV2 {
  @Autowired
  private TodoService todoService;
  // @Autowired
  // private TodoRepository todoRepository;

  @PostMapping("/saveTodoItem")
  public Todo saveTodoItem(@RequestBody Todo todo) throws Exception {
    return todoService.saveTodoItem(todo);
  }

  @PostMapping("/batchSaveTodoItem")
  public void batchSaveTodoItem() throws Exception {
    List<Todo> todos = new ArrayList<Todo>();

    for(int i = 0; i < 20; i++){
      Todo todo = new Todo();
      todo.setPk("pk" + Integer.toString(i));
      todo.setSk("sk" + Integer.toString(i));
      todos.add(todo)
;    }

    todoService.batchSaveTodoItem(todos);
  }

  @GetMapping("/queryTodoByPartitionKey")
  public List<Todo> queryTodoByPartitionKey(@RequestParam(value = "pk", defaultValue = "pk") String pk)
      throws IllegalAccessException, InstantiationException, ClassNotFoundException,
      DDBModelException, NOKeyException, ParseException, IOException {
        Todo todo = new Todo();
        todo.setPk(pk);
    return todoService.queryTodoByPartitionKey(todo);
  }

  @GetMapping("/queryTodoByRangeKey")
  public List<Todo> queryTodoByRangeKey(@RequestParam(value = "pk", defaultValue = "pk") String pk,
  @RequestParam(value = "sk", defaultValue = "sk") String sk)
      throws IllegalAccessException, InstantiationException, ClassNotFoundException,
      DDBModelException, NOKeyException, ParseException, IOException {
        Todo todo = new Todo();
        todo.setPk(pk);
        todo.setSk(sk);
    return todoService.queryTodoByRangeKey(todo);
  }

  @PostMapping("/updateTodoDone")
  public Todo updateTodoDone(@RequestBody Todo todo) throws IllegalAccessException,
      DDBModelException, ClassNotFoundException, InstantiationException, ParseException {
    return todoRepository.updateDone(todo);
  }

}
