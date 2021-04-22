package com.vincent.practice.test;

interface Shape {
  double area();

  void showResult();
}


class Rectangle implements Shape {
  double len, width;

  public Rectangle() {}

  public Rectangle(int x, int y) {
    len = x;
    width = y;
  }

  public double area() {
    return len * width;
  }

  public void showResult() {
    System.out.println("矩形長：" + len);
    System.out.println("矩形寬：" + width);
    System.out.println("矩形面積：" + area());
  }
}


class Triangle implements Shape {
  double base, height;

  public Triangle() {}

  public Triangle(double b, double h) {
    base = b;
    height = h;
  }

  public double area() {
    return (base * height) / 2;
  }

  public void showResult() {
    System.out.println("三角形底：" + base);
    System.out.println("三角形高：" + height);
    System.out.println("三角形面積：" + area());
  }
}


class Circle implements Shape {
  double r;

  public Circle(double r) {
    this.r = r;
  }

  public double area() {
    return Math.PI * r * r;
  }

  public void showResult() {
    System.out.println("圓半徑：" + r);
    System.out.println("圓面積：" + area());
  }
}


class TestInf {
  public static void main(String[] arg) {
    new Rectangle(10, 20).showResult();
    new Triangle(18.5, 20).showResult();
    new Circle(19.8).showResult();
  }
}
