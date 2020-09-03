package com.vincent.practice.controller;

import java.text.ParseException;
import com.vincent.practice.model.Todo;
import com.vincent.practice.repository.TodoRepository;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/todo/v2")
public class TodoListControllerV2 {
  @Autowired
  private TodoRepository todoRepository;

  @PostMapping("/saveTodo")
  public Todo saveTodo(@RequestBody Todo todo)
      throws IllegalAccessException, InstantiationException, ClassNotFoundException,
      DDBModelException, NOKeyException, ParseException {
    return todoRepository.saveTodoItem(todo);
  }

  @PostMapping("/testGet")
  public Todo testGet(@RequestParam(value = "pk2", defaultValue = "pk2") String pk,
      @RequestParam(value = "sk2", defaultValue = "sk2") String sk)
      throws IllegalAccessException, InstantiationException, ClassNotFoundException,
      DDBModelException, NOKeyException, ParseException {
    return todoRepository.testGet(pk, sk);
  }

  @PostMapping("/testSave")
  public Todo testSave(@RequestParam(value = "pk", defaultValue = "pk") String pk,
      @RequestParam(value = "sk", defaultValue = "sk") String sk,
      @RequestParam(value = "todo", defaultValue = "todo") String desc)
      throws IllegalAccessException, DDBModelException, NOKeyException {
    Todo todo = new Todo();
    todo.setPk(pk);
    todo.setSk(sk);
    todo.setTask(desc);
    return todoRepository.saveTodoItem(todo);
  }

  @PostMapping("/updateTodoDone")
  public Todo updateTodoDone(@RequestBody Todo todo) throws IllegalAccessException,
      DDBModelException, ClassNotFoundException, InstantiationException, ParseException {
    return todoRepository.updateDone(todo);
  }

}
