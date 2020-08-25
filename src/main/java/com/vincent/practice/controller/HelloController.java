package com.vincent.practice.controller;

import java.util.concurrent.atomic.AtomicLong;
import com.vincent.practice.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  private static final String TEMPLATE = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("hello %s!", name);
  }

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name)); // response as JSON
  }

}
