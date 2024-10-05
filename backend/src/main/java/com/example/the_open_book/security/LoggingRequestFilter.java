package com.example.the_open_book.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * LoggingRequestFilter
 */
@Component
public class LoggingRequestFilter extends OncePerRequestFilter {

  private Logger logger = LoggerFactory.getLogger(this.getClass());


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {



    filterChain.doFilter(request, response);
  }



}
