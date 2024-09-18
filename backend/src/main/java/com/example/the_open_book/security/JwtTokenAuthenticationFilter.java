package com.example.the_open_book.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.the_open_book.user.UserRepository;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JwtTokenAuthenticationFilter
 */
@Service
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
  private final UserRepository userRepository;
  private final JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtil.isNullOrEmpty(header) || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Get jwt token and validate
    final String token = header.split(" ")[1].trim();
    var userDetails = userRepository
        .findByEmail(jwtTokenUtil.getUsername(token))
        .orElse(null);

    if (userDetails == null || !jwtTokenUtil.isValidToken(token, userDetails)) {
      filterChain.doFilter(request, response);
      return;
    }

    // Get user identity and set it on the spring security context
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null,
        userDetails.getAuthorities());

    authentication.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);


    filterChain.doFilter(request, response);
  }

}
