package com.vincent.practice.sample;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

  // @RequestMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
  // public String index() {
  // return "Greetings from Spring Boot!";
  // }

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("hello %s!", name);
  }

}
