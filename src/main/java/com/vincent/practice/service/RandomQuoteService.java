package com.vincent.practice.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RandomQuoteService {
  final FileQuoteList quote = new FileQuoteList();

  public String getRandomQuote() throws FileNotFoundException, IOException {
    List<String> quoteList = quote.getQuoteList();
    Random random = new Random();
    int index = random.nextInt(quoteList.size());
    return (String) quoteList.get(index);
  }
}
