package com.vincent.practice;


public class EnumExample {
  public enum Modes {
    ALPHA, BETA;
  }

  enum Season {
    WINTER(5), SPRING(10), SUMMER(15), FALL(20);

    private int value;

    private Season(int value) {
      this.value = value;
    }

    public boolean isWinter() {
      return this.value == 5;
    }
  }

  public static void main(String[] args) {
    System.out.println(Modes.ALPHA);
    System.out.println(Modes.BETA);
    System.out.println(Season.WINTER.isWinter());
    System.out.println(Season.SPRING);
    System.out.println(Season.SUMMER);
    System.out.println(Season.FALL);
    System.out.println(Season.WINTER.value);
    System.out.println(Season.SPRING.value);
    System.out.println(Season.SUMMER.value);
    System.out.println(Season.FALL.value);
  }


}
