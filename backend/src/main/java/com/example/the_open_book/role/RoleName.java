package com.example.the_open_book.role;

import lombok.Getter;

/**
 * RoleName
 */
@Getter
public enum RoleName {
  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN");

  private final String name;

  RoleName(String name) {
    this.name = name;
  }
}
