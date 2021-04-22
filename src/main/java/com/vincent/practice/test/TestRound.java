package com.vincent.practice.test;

abstract class Round{
  double r;
  public Round(){}
  public Round(double r){
      this.r = r;
  }
  public double area(){
      return Math.PI*r*r;
  }
  public abstract void showResult();
}
class Circle3 extends Round{
  public Circle3(){}
  public Circle3(double x){
      super(x);
  }
  public void showResult(){
      System.out.println("圓半徑："+super.r);
      System.out.println("圓面積："+super.area());
  }
}
class Cylinder3 extends Round{
  double h;
  public Cylinder3(){}
  public Cylinder3(double x,double y){
      super(x);
      this.h=y;
  }
  public double area(){
      return super.area()*h;
  }
  public void showResult(){
      System.out.println("圓柱半徑："+super.r);
      System.out.println("圓柱高："+h);
      System.out.println("圓柱體積："+super.area());
  }
} 

class TestRound{
  public static void main(String[] arg){
      Circle3 c1=new Circle3(10.5);
      c1.showResult();
      Cylinder3 c2=new Cylinder3(10,20);
      c2.showResult();
  }
}

