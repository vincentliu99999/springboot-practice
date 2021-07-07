package com.vincent.practice;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class PracticeApplication {

  private static final Logger logger = LoggerFactory.getLogger(PracticeApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(PracticeApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    return args -> {

      // System.out.println("Let's inspect the beans provided by Spring Boot:");

      String[] beanNames = ctx.getBeanDefinitionNames();
      // ctx.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
      // .forEach((key, value) -> logger.info("key:{} value {}", key, value));
      Arrays.sort(beanNames);
      for (String beanName : beanNames) {
        // System.out.println(beanName);
      }

    };
  }

  // @Bean
  // public RestTemplate restTemplate(RestTemplateBuilder builder) {
  //   // uses the Jackson JSON processing library to process the incoming data.
  //   return builder.build();
  // }

  // @Bean
  // public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
  //   // fetch random quote on startup
  //   return args -> {
  //     Quote quote =
  //         restTemplate.getForObject("https://quoters.apps.pcfone.io/api/random", Quote.class);
  //     logger.info(quote.toString());
  //   };
  // }

}
