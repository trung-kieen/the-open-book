package com.example.the_open_book.exception;

import java.util.HashMap;
import java.util.HashSet;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.thymeleaf.standard.expression.MessageExpression;

import com.example.the_open_book.payload.ResponseResult;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;

/**
 * GlobalExceptionHandler
 */
@RestControllerAdvice
@Log4j2
public class GlobalWebExceptionHandler {

  @ExceptionHandler(LockedException.class)
  public ResponseEntity<?> handleLockedUserException(LockedException ex ){
    return ResponseResult.error(ErrorMessage.ACCOUNT_LOCKED).toResponseEntity();
  }

  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<?> handleDisabledUserException(DisabledException ex ){
    return ResponseResult.error(ErrorMessage.ACCOUNT_DISABLE).toResponseEntity();
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentialsUserException(BadCredentialsException ex ){
    return ResponseResult.error(ErrorMessage.INVALID_LOGIN_CREDENTIAL).toResponseEntity();
  }

  /**
   * Handle exception while sending email to user
   */
  @ExceptionHandler(MessagingException.class)
  public ResponseEntity<?> handleEmailMessageSendingException(MessagingException ex ){
    var rs = ResponseResult.error(ErrorMessage.EMAIL_MESSAGING_NOT_SEND , ex.getMessage()).toResponseEntity();
    return rs;
  }


  /**
   * Handle for request not due to spring-validation constraint
   * Any payload request that use @Valid might raise this error
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleNotValidArgumentException(MethodArgumentNotValidException ex ){
    var errors =  new HashSet<>();
    ex.getFieldErrors().forEach(error -> {
      errors.add(error.getDefaultMessage());
    });
    var rs = ResponseResult.error(ErrorMessage.EMAIL_MESSAGING_NOT_SEND);
    rs.setValidationError(errors);
    return rs.toResponseEntity();
  }

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<?> otherApplicaitonException(ApplicationException ex ){
    var rs = ResponseResult.error(ErrorMessage.APPLICATION_ERROR);
    log.info("Application error {}" , ex.getMessage());
    return rs.toResponseEntity();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> unknowException(Exception ex ){
    var rs = ResponseResult.error(ErrorMessage.UNKNOW);
    return rs.toResponseEntity();
  }
}
