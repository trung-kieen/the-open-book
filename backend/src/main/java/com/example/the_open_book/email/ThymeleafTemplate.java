package com.example.the_open_book.email;

import lombok.Getter;

/**
 * ThymeleafTemplate
 */
@Getter
public enum ThymeleafTemplate {
  ACTIVE_ACCOUNT("active_account");

  private String templateFileHtml;

  ThymeleafTemplate(String fileName) {
    this.templateFileHtml = fileName;
  }

}
