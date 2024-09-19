package com.example.the_open_book.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StaticResourceNotDefinedException for notice error when find a resource
 * initialize by default in application
 */
public class StaticResourceNotDefinedException extends ApplicationException {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public StaticResourceNotDefinedException() {
    super();
  }

  public StaticResourceNotDefinedException(String message, Throwable cause) {
    super(message, cause);
  }

  public StaticResourceNotDefinedException(String message) {
    super(message);
  }

  public StaticResourceNotDefinedException(String message, Object lookupResource) {
    super(message);
    logger.info("Not found resource {}", lookupResource.toString());
  }
  public StaticResourceNotDefinedException(Object lookupResource) {
    super();
    logger.info("Not found resource {}", lookupResource.toString());
  }

  public StaticResourceNotDefinedException(Throwable cause) {
    super(cause);
  }

}
