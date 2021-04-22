package com.vincent.practice.test;

class Overloading1{
  public void method1(int x){
      System.out.println("method1(int)");
  }
  
  public void method1(int x, int y){
      System.out.println("method1(int, int)");
  }
  
  public void method1(double x, double y){
      System.out.println("method1(double, double)");
  }
  
  public void method2(int x, double y){
      System.out.println("method2(int, double)");
  }
  
  public void method2(double x, int y){
      System.out.println("method2(double, int)");
  }
  
  public static void main(String[] arg){
      Overloading1 obj = new Overloading1();
      System.out.println("基本資料型別的多載 ~ 個數不同、型別不同、順序不同");
      obj.method1(10);
      obj.method1(10, 20);
      obj.method1(10.0, 20.0);
      
      obj.method2(10, 20.0);
      obj.method2(10.0, 20);
  }
}

