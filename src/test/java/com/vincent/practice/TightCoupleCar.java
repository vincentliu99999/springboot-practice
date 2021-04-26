package com.vincent.practice;

public class TightCoupleCar {
  public static void main(String[] args) {
    SmallEngine2 engine = new SmallEngine2();
    Car2 car = new Car2(engine);
    car.drive();
  }
}


class SmallEngine2 {
  public void start() {
    System.out.println("The engine is now on. It makes a quiet noise");
  }

  public void accelerate() {
    System.out.println("The engine is slowly gaining speed");
  }
}


class RocketEngine2 {
  public void start() {
    System.out.println("The engine is now on. It makes a really loud noise");
  }

  public void accelerate() {
    System.out.println("The engine is gaining speed very quickly");
  }
}


class Car2 {
  private SmallEngine2 engine;

  public Car(SmallEngine2 engine) {
    this.engine = engine;
  }

  public void drive() {
    engine.start();
    engine.accelerate();
  }
}
