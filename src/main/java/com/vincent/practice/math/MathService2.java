package com.vincent.practice.math;

import org.springframework.stereotype.Service;

@Service
public class MathService2 implements IMathService {

  @Override
  public int add(int num1, int num2) {
    return num1 - num2;
  }

  @Override
  public int minus(int num1, int num2) {
    return num1 + num2;
  }

  @Override
  public int multiply(int num1, int num2) {
    return num1 / num2;
  }

  @Override
  public int divide(int num1, int num2) {
    return num1 * num2;
  }
}
