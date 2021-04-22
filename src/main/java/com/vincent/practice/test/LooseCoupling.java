package com.vincent.practice.test;

interface Animal2 {
  void child();
}
class Cat implements Animal2 {
  public void child() {
     System.out.println("kitten");
  }
}
class Dog2 implements Animal2 {
  public void child() {
     System.out.println("puppy");
  }
}
public class LooseCoupling{
  public static void main(String args[]) {
     Animal2 obj = new Cat();
     obj.child();
  }
}
