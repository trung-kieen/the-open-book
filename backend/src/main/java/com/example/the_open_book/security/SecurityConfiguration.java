package com.example.the_open_book.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * SecurityConfiguration
 * @see AuthenticationProviderConfiguration
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
  @Value("${spring.security.enabled:true}")
  private boolean securityEnabled;

  private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

  private static final String[] AUTH_WHITELIST = {
      // -- Swagger UI v2
      "**.html",
      "/v2/api-docs",
      "v2/api-docs",
      "swagger-ui/index.html",
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
      "/auth",
      "/health/**",
      "/test/**",
  };



  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    if (securityEnabled) {
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
        .addFilterBefore(jwtTokenAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class)

          // .exceptionHandling(exceptionHandlingCustomizer)
          // TODO
        .formLogin(Customizer.withDefaults());

        } else {
            httpSecurity.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll())
                    .csrf(csrf -> csrf.disable());
        }
    return httpSecurity.build();

  }



}
