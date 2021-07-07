package com.vincent.practice.model;

import java.util.Date;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.vincent.practice.repository.ddbmapper.annotation.DDBAttr;
import com.vincent.practice.repository.ddbmapper.annotation.DDBHashKey;
import com.vincent.practice.repository.ddbmapper.annotation.DDBHashKey.KEY_GEN;
import com.vincent.practice.repository.ddbmapper.annotation.DDBRangeKey;
import com.vincent.practice.repository.ddbmapper.annotation.DDBTable;

@DynamoDBTable(tableName = "Todo")
@DDBTable(name = "Todo")
public class Todo {

  @DynamoDBHashKey
  @DDBHashKey(name = "pk", gen = KEY_GEN.NUM_STR)
  private String pk;
  @DynamoDBRangeKey
  @DDBRangeKey(name = "sk")
  private String sk;
  private String task;
  private boolean done = false;
  @DDBAttr(name="createDate", updateable = false)
  private Date createDate = new Date();
  private Date updateDate = new Date();

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

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

}