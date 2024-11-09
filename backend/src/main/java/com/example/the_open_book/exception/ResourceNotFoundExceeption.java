package com.example.the_open_book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResourceNotFoundExceeption
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceeption extends ApplicationException{
  public ResourceNotFoundExceeption(String message){
    super(message);
  }



}
