package com.example.the_open_book.exception;

import java.util.Set;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ErrorResponse
 * Use to build response payload
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) // Only inclue none empty field as result else ignore field in json file
public class ErrorResponse {

  private Integer businessErrorCode;
  private String bussinessDescriptionCode;

  private String errorMessage;
  // Use for error from method argument not appropriate. This error raise base on @Valid validation
  private Set<String>  validationErrors;

  private Map<String , String > errors;
}
