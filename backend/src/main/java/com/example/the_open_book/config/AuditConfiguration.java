package com.example.the_open_book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * AuditConfiguration
 */
@Configuration
public class AuditConfiguration {
  @Bean
  public AuditorAware<Long> auditAware(){
    return new EntityAuditWare();
  }

}
