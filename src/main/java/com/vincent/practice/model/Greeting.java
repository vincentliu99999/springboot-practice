package com.vincent.practice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "打招呼 id 為 counter")
public class Greeting {
  @ApiModelProperty(value = "id")
  private final long id;

  @ApiModelProperty(value = "first name of the user", example = "Vatsal", required = true)
  private final String content;

  public Greeting(long id, String content) {
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
