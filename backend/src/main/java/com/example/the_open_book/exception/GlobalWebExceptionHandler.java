package com.example.the_open_book.exception;

import java.util.HashSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;

/**
 * GlobalExceptionHandler
 */
@RestControllerAdvice
@Log4j2
public class GlobalWebExceptionHandler {

  @ExceptionHandler(LockedException.class)
  public ResponseEntity<?> handleLockedUserException(LockedException ex){
    var errBusiness  = BusinessError.ACCOUNT_LOCKED;
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    .body(ErrorResponse.builder()
      .bussinessDescriptionCode(errBusiness.getDescription())
      .businessErrorCode(errBusiness.getCode())
      .errorMessage(ex.getMessage())
      .build());
  }

  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<?> handleDisabledUserException(DisabledException  ex){
    var errBusiness  = BusinessError.ACCOUNT_DISABLED;
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    .body(ErrorResponse.builder()
      .bussinessDescriptionCode(errBusiness.getDescription())
      .businessErrorCode(errBusiness.getCode())
      .errorMessage(ex.getMessage())
      .build());
  }




  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredential(BadCredentialsException ex){
    var errBusiness  = BusinessError.BAD_CREDENTIAL;
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    .body(ErrorResponse.builder()
      .bussinessDescriptionCode(errBusiness.getDescription())
      .businessErrorCode(errBusiness.getCode())
      .errorMessage(ex.getMessage())
      .build());
  }


  /**
   * Handle email service sending mail exception
   */
  @ExceptionHandler(MessagingException.class)
  public ResponseEntity <?> handleEmailSendingException(MessagingException ex){

    // var errBusiness = BusinessError.
    var err = ErrorResponse.builder()
    .errorMessage(ex.getMessage())
    .build();
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
    .body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleInvalidPayload(MethodArgumentNotValidException ex){
    var validationErrors =  new HashSet<String>();
    ex.getFieldErrors().stream().forEach(
      e -> {
        validationErrors.add(e.getField()  + ": " + e.getDefaultMessage());
      }
    );

    var err = ErrorResponse.builder()
    .validationErrors(validationErrors)
    .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    .body(err);

  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> unhandleException(Exception ex ){
    log.info("Unhandle error of class : {}" , ex.getClass().getName());
    log.info("Unhandle error message: {}" , ex.getMessage());
    // ex.printStackTrace();

    var err = ErrorResponse.builder()
    .errorMessage("Internal application error")
    .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    .body(err);


  }


}
