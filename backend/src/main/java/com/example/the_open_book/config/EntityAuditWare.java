package com.example.the_open_book.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.the_open_book.user.User;

import lombok.RequiredArgsConstructor;

/**
 * EntityAudit
 * When enitty register audit event it will call to this class to trace update, create user id
 * Must impl AuditorAware interface
 * https://stackoverflow.com/questions/66498894/spring-boot-auditing-map-current-user-to-createdby-lastmodifiedby
 * Need to supple @EnableJpaAuditing in configuration class
 */
@RequiredArgsConstructor

public class EntityAuditWare implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {

    /*
     * Make sure user is authentication with a valid user id so we can get his/her user_id
     */
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated() || (auth instanceof AnonymousAuthenticationToken)) {
      return Optional.empty();
    }
    /*
     * Return their user id as Optional object
     */
    var user = (User) auth.getPrincipal();
    return Optional.ofNullable(user.getUserId());


  }

}
