package com.vincent.practice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.vincent.practice.controller.TodoListControllerV2;
import com.vincent.practice.model.Todo;
import com.vincent.practice.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(TodoListControllerV2.class)
public class TodoListControllerV2Test {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TodoService todoService;

  @Test
  public void saveTodoItem() throws Exception {
    RequestBuilder request = MockMvcRequestBuilders
    .post("/todo/v2/saveTodoItem")
    .contentType(MediaType.APPLICATION_JSON)
    .content("{ \"pk\": \"pk\", \"sk\": \"sk\" }")
    .accept(MediaType.APPLICATION_JSON);
  
  MvcResult result = mockMvc.perform(request)
    .andExpect(status().isOk())
    .andExpect(content().string(""))
    .andReturn();
  }

  @Test
  public void getTodoByPkBySk() throws Exception {
    when(todoService.getTodoByPkBySk("pk", "sk")).thenReturn(
      new Todo()
    );

    RequestBuilder request = MockMvcRequestBuilders
    .get("/todo/v2/getTodoByPkBySk")
    .contentType(MediaType.APPLICATION_JSON)
    .content("{ \"pk\": \"pk\", \"sk\": \"sk\" }")
    .accept(MediaType.APPLICATION_JSON);
  
  MvcResult result = mockMvc.perform(request)
    .andExpect(status().isOk())
    // .andExpect(content().string(""))
    .andReturn();
  }
}
