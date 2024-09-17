package com.example.the_open_book.security;

import java.io.IOException;

import org.springframework.stereotype.Service;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * JwtTokenAuthenticationFilter
 */
@Service
public class JwtTokenAuthenticationFilter implements Filter {

  @Override
  public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
      throws IOException, ServletException {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'doFilter'");
  }


}
