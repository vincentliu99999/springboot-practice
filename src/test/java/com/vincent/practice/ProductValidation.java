package com.vincent.practice;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ProductValidation {
  public static void main(String[] args) {
    Product product = new Product();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Product>> violations = validator.validate(product);
    for (ConstraintViolation<Product> violation : violations) {
      System.out.println(violation.getMessage());
    }
  }
}
