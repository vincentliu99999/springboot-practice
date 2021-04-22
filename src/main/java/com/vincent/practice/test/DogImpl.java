package com.vincent.practice.test;

interface Pet {
  public void test();
}
class DogImpl implements Pet {
  public void test() {
      System.out.println("Interface Method Implemented");
  }
  public static void main(String args[]) {
      Pet p = new DogImpl();
      p.test();
  }
}
