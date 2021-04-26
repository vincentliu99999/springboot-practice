package com.vincent.practice;

public class LooseCoupleCar {
  public static void main(String[] args) {
    Engine engine = new RocketEngine();
    Car car = new Car(engine);
    car.drive();
  }
}


interface Engine {
  void start();

  void accelerate();
}


class RocketEngine implements Engine {
  public void start() {
    System.out.println("The engine is now on. It makes a really loud noise");
  }

  public void accelerate() {
    System.out.println("The engine is gaining speed very quickly");
  }
}


class SmallEngine implements Engine {
  public void start() {
    System.out.println("The engine is now on. It makes a quiet noise");
  }

  public void accelerate() {
    System.out.println("The engine is slowly gaining speed");
  }
}


class Car {
  private Engine engine;

  public Car(Engine engine) {
    this.engine = engine;
  }

  public void drive() {
    engine.start();
    engine.accelerate();
  }
}
