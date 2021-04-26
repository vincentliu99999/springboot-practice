package com.vincent.practice.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class FileQuoteList {

  final String fileName = "files/quote.txt";
  final File file = getFileFromResource(fileName);

  public List<String> getQuoteList() {

    try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {
      return br.lines().collect(Collectors.toList());
    } catch (IOException e) {
      return new ArrayList<String>();
    }
  }

  File getFileFromResource(String fileName) {

    File quotes = null;
    Resource resource = new ClassPathResource(fileName);

    try {
      quotes = resource.getFile();
    } catch (IOException e) {
      e.printStackTrace();
      return quotes;
    }

    return quotes;
  }
}
