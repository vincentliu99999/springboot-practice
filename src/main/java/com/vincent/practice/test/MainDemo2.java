package com.vincent.practice.test;

// Java Program to demonstrate
// the above steps

// Here, Geek is a class
class Geek {

  // Until no request the
  // value will be false
  boolean value = false;

  private String request = "";

  // Constructor of Geek
  Geek(String request) {
    // The value becomes true
    // once the constructor called
    // or when the object created
    value = true;

    this.request = request;

    System.out.println("Geek Requested: " + request);
  }

  public String getRequest() {
    return this.request;
  }
}

// A google class
class Google {

  private String request;

  // Constructor of Google
  Google(String request) {
    this.request = request;
    System.out.println("Google Searched: " + request);
  }

  // This class may also contains methods
  // through which the request passes
  // messages to further more objects
  // and also send back a response
}


// Demo class to understand
// A way of using abstraction
public class MainDemo2 {

  // Driver code
  public static void main(String[] args) {
    // Geek class object creation
    Geek g = new Geek("Java");

    // Checking whether Geek
    // has requested or not
    if (g.value) {

      // If the Geek requested
      // Google object created
      Google gl = new Google(g.getRequest());

      // Google performs some action
      // To satisfy the Geek with his
      // Desired result
    }
  }
}
