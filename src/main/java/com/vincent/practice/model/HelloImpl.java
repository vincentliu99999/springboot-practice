package com.vincent.practice.model;

public class HelloImpl implements IHello {

  @Override
  public void greeting(String word) {
    System.out.println("greeting: " + word);
  }

  @Override
  public void goodbye(String word) {
    System.out.println("goodbye: " + word);
  }

  public void sss(String word) {
    System.out.println("goodbye: " + word);
  }
  
}
