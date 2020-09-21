package com.vincent.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/springboot")

public class SpringBootController {
  @Autowired
  private Environment environment;

  @Value("${spring.profiles.active}")
  private String activeProfiles;

  @GetMapping("/getEnvironmentByObject")
  public String[] getEnvironmentByObject() {
    return environment.getActiveProfiles();
  }

  @GetMapping("/getEnvironmentByProfile")
  public String getEnvironmentByProfile() {
    return this.activeProfiles;
  }
}
