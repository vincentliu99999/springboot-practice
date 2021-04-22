package com.vincent.practice.test;

public class TestRelatable {
  public static void main(String[] args) {
    Relatable obj1 = new RectanglePlus();
    Relatable obj2 = new RectanglePlus();

    System.out.println(findLargest(obj1, obj2));
    System.out.println(findSmallest(obj1, obj2));
    System.out.println(isEqual(obj1, obj2));
  }
  public static Object findLargest(Object object1, Object object2) {
    Relatable obj1 = (Relatable)object1;
    Relatable obj2 = (Relatable)object2;
    if ((obj1).isLargerThan(obj2) > 0)
       return true;
    else 
       return false;
  }

  public static Object findSmallest(Object object1, Object object2) {
    Relatable obj1 = (Relatable)object1;
    Relatable obj2 = (Relatable)object2;
    if ((obj1).isLargerThan(obj2) < 0)
       return true;
    else 
       return false;
 }
 
 public static boolean isEqual(Object object1, Object object2) {
    Relatable obj1 = (Relatable)object1;
    Relatable obj2 = (Relatable)object2;
    if ( (obj1).isLargerThan(obj2) == 0)
       return true;
    else 
       return false;
 }
}
