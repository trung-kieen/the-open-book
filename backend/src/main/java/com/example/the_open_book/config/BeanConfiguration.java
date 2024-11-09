package com.example.the_open_book.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BeanConfiguration
 */
@Configuration
public class BeanConfiguration {
  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }


}
