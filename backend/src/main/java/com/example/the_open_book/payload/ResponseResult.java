package com.example.the_open_book.payload;

import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.the_open_book.exception.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * ResponseResult
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class ResponseResult<T> {

  private Integer businessErrorCode;
  @Setter
  private String description;
  private String message;
  private HttpStatus status;
  @Setter
  private Set<String> validationError; // TODO: Explain purpose
  @Setter
  private Map<String, String> errors;
  @Setter
  private T data;

  // public ResponseResult(ErrorMessage errorMessage){
  // }

  public static <T> ResponseResult<T> error(ErrorMessage errorMessage, T data) {
    var result = ResponseResult.<T>builder()
        .message(errorMessage.getMessage())
        .businessErrorCode(errorMessage.getErrorCode())
        .status(errorMessage.getStatus())
        .data(data)
        .build();
    return result;
  }

  public static ResponseResult error(ErrorMessage errorMessage) {
    var result = ResponseResult.builder()
        .message(errorMessage.getMessage())
        .businessErrorCode(errorMessage.getErrorCode())
        .status(errorMessage.getStatus())
        .build();
    return result;
  }

  public ResponseResult<T> data(T data){
    this.data = data;
    return this;
  }
  public ResponseEntity<?> toResponseEntity() {
    if (this.status.equals(HttpStatus.INTERNAL_SERVER_ERROR) && data != null)
      log.info("Internal server error relate with: {}", data);
    return ResponseEntity
        .status(status.value())
        .body(this);
  }

}
