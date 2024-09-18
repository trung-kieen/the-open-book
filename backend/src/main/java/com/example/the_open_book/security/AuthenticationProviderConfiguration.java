package com.example.the_open_book.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

/**
 * AuthenticationProviderConfiguration
 */
@Configuration
@RequiredArgsConstructor
public class AuthenticationProviderConfiguration {

  private final UserDetailsService userDetailsService;

  /**
   * Configuration authentication merchandise with user detail service and
   * password encoder
   *
   * Common way is user DaoAuthenticationProvider to implement authentication for
   * Authentication object.
   *
   * @see <a href=
   *      "https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#customize-global-authentication-manager">spring
   *      document</a>
   *
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    return daoAuthenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
