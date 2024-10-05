package com.example.the_open_book.email;

import lombok.Getter;

/**
 * ThymeleafTemplate
 */
@Getter
public enum ThymeleafTemplate {
  ACTIVE_ACCOUNT("active_account", "Activation your account");

  private String templateFileHtml;
  private String emailSubject;

  ThymeleafTemplate(String fileName, String emailSubject) {
    this.templateFileHtml = fileName;
    this.emailSubject = emailSubject;
  }

}
