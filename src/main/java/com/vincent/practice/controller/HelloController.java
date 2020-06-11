package com.vincent.practice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

  @RequestMapping("/hello")
  public String hello() {
    return "hello";
  }

  @RequestMapping("/greeting")
  public String greeting() {
    return "greeting";
  }

  @RequestMapping("/greeting2")
  public String greeting2() {
    return "greeting2";
  }

}
