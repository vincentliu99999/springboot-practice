package com.vincent.practice.test;

class Gcd1 {
  static int gcdOf(int x, int y) {
    int r = 0;
    while (y != 0) {
      r = x % y;
      x = y;
      y = r;
    }
    return x;
  }
}


class TestGcd1 {
  public static void main(String[] arg) {
    int result = Gcd1.gcdOf(10, 5);
    System.out.println("10,5最大公因數：" + result);
    System.out.println("3,10最大公因數：" + Gcd1.gcdOf(3, 10));
  }
}

