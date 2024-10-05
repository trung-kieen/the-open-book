package com.example.the_open_book.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * ExceptionMessage
 */
@Getter
public enum ErrorMessage {

  NOT_CATAGORIED("No code" ,HttpStatus.FORBIDDEN ),


  // Auth
  ACCOUNT_LOCKED("User account is locked" ,HttpStatus.UNAUTHORIZED,  100),
  INVALID_LOGIN_CREDENTIAL("Username or password is not correct", HttpStatus.BAD_REQUEST, 101),
  NEW_PASSWORD_DOES_NOT_MATCH("New password is not match" ,HttpStatus.BAD_REQUEST,  102),
  ACCOUNT_DISABLE("User account is disabled" ,HttpStatus.UNAUTHORIZED,  103),
  INVALID_TOKEN("token is not valid " , HttpStatus.BAD_REQUEST , 104),





  // Service
  EMAIL_MESSAGING_NOT_SEND("Sending email occor error", HttpStatus.INTERNAL_SERVER_ERROR , 201 ),
  TOKEN_EXPIRE_RESEND_EMAIL("Activation email is expired please recheck email for new activation code", HttpStatus.BAD_REQUEST, 202 ),


  // Resource
  RESOURCE_NOT_FOUND("Resource not found" , HttpStatus.NOT_FOUND, 300),
  STATIC_RESOURCE_NOT_INITIALIZE("Require to init static resource for application operation " , HttpStatus.INTERNAL_SERVER_ERROR, 301),



  UNKNOW("Unknow error occur", HttpStatus.INTERNAL_SERVER_ERROR, 401 ),
  APPLICATION_ERROR("Unknow error occur", HttpStatus.BAD_REQUEST, 402 ),

  ;

  private final String message;
  private final HttpStatus status;
  private final Integer errorCode;
  private ErrorMessage(String message, HttpStatus status, Integer errorCode) {
    this.message = message;
    this.status = status;
    this.errorCode = errorCode;
  }
  private ErrorMessage(String message, HttpStatus status) {
    this.errorCode = -1;
    this.message = message;
    this.status = status;
  }
}
