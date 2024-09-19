package com.example.the_open_book.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * SecurityConfiguration
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {


  private final AuthenticationProvider authenticationProvider;
  private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

  private static final String[] AUTH_WHITELIST = {
      // -- Swagger UI v2
      "**.html",
      "/v2/api-docs",
      "v2/api-docs",
      "/swagger-resources",
      "swagger-resources",
      "/swagger-resources/**",
      "swagger-resources/**",
      "/configuration/ui",
      "configuration/ui",
      "/configuration/security",
      "configuration/security",
      "/swagger-ui.html",
      "swagger-ui.html",
      "webjars/**",
      // -- Swagger UI v3
      "/v3/api-docs/**",
      "v3/api-docs/**",
      "/swagger-ui/**",
      "swagger-ui/**",
      // CSA Controllers
      "/csa/api/token",
      // Actuators
      "/actuator/**",
      "/auth/**",
      "/health/**"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .cors(Customizer.withDefaults())
        .csrf(c -> c.disable()) // Disable csrf must enable jwt token for protection
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers(AUTH_WHITELIST).permitAll()
            // .requestMatchers("/**").hasRole("USER")
            .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        /*
         * Avoid application authorize user has login with session to deal with csrf
         * attack when we disable csrf
         */
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtTokenAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class)

        .formLogin(Customizer.withDefaults());
    return httpSecurity.build();

  }

}
