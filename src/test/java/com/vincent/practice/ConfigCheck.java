package com.vincent.practice;

import java.util.ArrayList;
import java.util.List;

public class ConfigCheck {
  public static void main(String[] args) {
    List<String> properties = new ArrayList<>();
    properties.add("java.version");
    properties.add("java.vendor");
    properties.add("java.vendor.url");
    properties.add("java.home");
    properties.add("java.vm.specification.version");
    properties.add("java.vm.specification.vendor");
    properties.add("java.vm.specification.name");
    properties.add("java.vm.version");
    properties.add("java.vm.vendor");
    properties.add("java.vm.name");
    properties.add("java.specification.version");
    properties.add("java.specification.vendor");
    properties.add("java.specification.name");
    properties.add("java.class.version");
    properties.add("java.class.path");
    properties.add("java.library.path	");
    properties.add("java.io.tmpdir");
    properties.add("java.compiler");
    properties.add("java.ext.dirs");
    properties.add("os.name");
    properties.add("os.arch");
    properties.add("os.version");
    properties.add("file.separator");
    properties.add("path.separator");
    properties.add("line.separator");
    properties.add("user.name");
    properties.add("user.home");
    properties.add("user.dir");
    System.getProperties();

    for(String key: properties) {
      System.out.println(key + "\t: \t" + System.getProperty(key));
    }
  }
}
