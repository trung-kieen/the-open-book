package com.example.the_open_book.exception;

import lombok.Getter;

/**
 * BusinnesException
 * Use for exception with predefine message in {@link ErrorMessage}
 */
@Getter
public class BusinnesException extends ApplicationException {
  private ErrorMessage errorMessage;
  private Object data;

  public <T> BusinnesException(ErrorMessage errorMessage , T data ) {
    this.errorMessage = errorMessage;
    this.data = data;
  }

  public <T> BusinnesException(ErrorMessage errorMessage ) {
    this.errorMessage = errorMessage;
    this.data = null;
  }

}
