package com.example.the_open_book.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * AbstractAuditEntity
 */

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractAuditEntity extends BaseEntity {

  @CreatedDate
  @JsonIgnore
  @Column(name = "create_at", nullable =  false , updatable =  false )
  private LocalDateTime createAt;

  @LastModifiedDate
  @JsonIgnore
  @Column(name = "last_modified_date", nullable =  false , insertable =  false )
  private LocalDateTime lastModifiedDate;


  /*
   * TODO: Make generic type T for user from audit and audit ip, hostname, etc
  * https://stackoverflow.com/questions/61813715/spring-boot-auditing-hostname-and-hostip
  */

  @CreatedBy
  @JsonIgnore
  private Long createdBy;

  @Column(name =  "last_modified_by")
  @JsonIgnore
  @LastModifiedBy
  private Long lastModifiedBy;

}
