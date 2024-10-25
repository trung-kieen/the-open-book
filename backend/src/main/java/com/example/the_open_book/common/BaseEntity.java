package com.example.the_open_book.common;

import java.io.Serializable; import java.time.LocalDateTime;

import org.antlr.v4.runtime.misc.Interval;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * BaseEntity
 */
// @Getter
// @Setter
@SuperBuilder
// @AllArgsConstructor
@NoArgsConstructor
// @MappedSuperclass
// @EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable{

  // NOTE: use column is like book_id instead of id so this field is deprecate
  // @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  // private String id ;


}
