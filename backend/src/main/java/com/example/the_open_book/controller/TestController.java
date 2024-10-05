package com.example.the_open_book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.the_open_book.token.Token;
import com.example.the_open_book.token.TokenRepository;

import lombok.RequiredArgsConstructor;

/**
 * TestController
 */
@RestController
@RequiredArgsConstructor
public class TestController {
  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private final TokenRepository tokenRepository;

  @GetMapping("test")
  @ResponseStatus(value  = HttpStatus.OK)
  public Object test(){
    // var t = Token.builder().token("123213").build();
    // tokenRepository.save(t);
    return tokenRepository.findAll();

  }

  @GetMapping("add")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void add(){
    var t  = Token.builder()
    .token("123456")
    .build();

    logger.info("{}",t );
    // tokenRepository.save(t);


  }


}
