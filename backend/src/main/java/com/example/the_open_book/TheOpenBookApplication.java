package com.example.the_open_book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class TheOpenBookApplication {

  public static void main(String[] args) {
    SpringApplication.run(TheOpenBookApplication.class, args);
  }
}
