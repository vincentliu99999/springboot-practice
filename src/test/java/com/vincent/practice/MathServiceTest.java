package com.vincent.practice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vincent.practice.math.MathService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PracticeApplication.class)
public class MathServiceTest {
  @Autowired private MathService mathService;

  @Test
  public void add() {
    int result = mathService.add(1, 2);
    assertEquals(3, result);
  }
}
