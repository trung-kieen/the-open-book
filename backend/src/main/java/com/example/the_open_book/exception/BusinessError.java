package com.example.the_open_book.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * BusinessError
 */

@Getter
public enum BusinessError {

  NO_CODE ( HttpStatus.NOT_IMPLEMENTED,1 ,   "Unknow defined exception" ),
  BAD_CREDENTIAL (  HttpStatus.BAD_REQUEST,   101 ,  "Login or password is not correct"),
  NEW_PASSWORD_IS_NOT_MATCH(  HttpStatus.BAD_REQUEST,   102 ,  "Password is not match"),
  ACCOUNT_DISABLED(  HttpStatus.FORBIDDEN,   103 ,  "User is disabled"),
  ACCOUNT_LOCKED (  HttpStatus.FORBIDDEN ,   100 ,  "User is locked")
  ;
  private Integer code ;
  private String description ;
  private HttpStatus httpStatus;
  private BusinessError(HttpStatus httpStatus, Integer code, String description) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.description = description;
  }

}
