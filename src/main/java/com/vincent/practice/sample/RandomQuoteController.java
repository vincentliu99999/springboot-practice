package com.vincent.practice.sample;

import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomQuoteController {

  RandomQuoteService randQ = new RandomQuoteService();

  @GetMapping("daily-quote")
  public String getMyQuote() {
    try {
      return randQ.getRandomQuote();
    } catch (IOException e) {
      return "To be or not to be";
    }
  }
}
