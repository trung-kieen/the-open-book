package com.example.the_open_book.security.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * AuthenticationExceptionHanlder
 * Can implements {@link AuthenticationFailureHandler} or {@link AuthenticationEntryPoint}
 */
@Log4j2
public class AuthenticationExceptionHanlder implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    log.info("Unhandle authentication exception occur {}" , exception.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED , exception.getMessage());

  }

}
