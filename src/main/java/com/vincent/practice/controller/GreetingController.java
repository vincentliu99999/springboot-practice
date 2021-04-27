package com.vincent.practice.controller;

import com.vincent.practice.model.Greeting;
import com.vincent.practice.model.PersonForm;
import io.swagger.annotations.ApiParam;
import java.util.concurrent.atomic.AtomicLong;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
  private static final String TEMPLATE = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
  }

  @PostMapping("/greetingForm")
  public PersonForm greeting(
      @ApiParam(value = "PersonForm", required = true) @Valid PersonForm person) {
    return person;
  }
}
