package com.example.the_open_book.common;

import java.io.Serializable;

import lombok.NoArgsConstructor;
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
