package com.vincent.practice.model;

import com.vincent.practice.repository.ddbmapper.annotation.DDBHashKey;
import com.vincent.practice.repository.ddbmapper.annotation.DDBRangeKey;
import com.vincent.practice.repository.ddbmapper.annotation.DDBTable;

@DDBTable(name = "Todo")
public class Todo {

  @DDBHashKey(name = "pk")
  private String pk;
  @DDBRangeKey(name = "sk")
  private String sk;
  private String todo;
  private String desc;

  public String getPk() {
    return pk;
  }

  public void setPk(String pk) {
    this.pk = pk;
  }

  public String getSk() {
    return sk;
  }

  public void setSk(String sk) {
    this.sk = sk;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getTodo() {
    return todo;
  }

  public void setTodo(String todo) {
    this.todo = todo;
  }
  
}