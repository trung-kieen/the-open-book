package com.example.the_open_book.actuator;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HttpTraceConfiguration
 */
@Configuration
public class HttpTraceConfiguration {

  @Bean
  public HttpExchangeRepository createTraceRepository() {
    return new InMemoryHttpExchangeRepository();
  }

}
