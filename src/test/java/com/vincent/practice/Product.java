package com.vincent.practice;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Product {
  private String id;

  @NotEmpty
  private String name;

  @Min(1)
  private int price;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
