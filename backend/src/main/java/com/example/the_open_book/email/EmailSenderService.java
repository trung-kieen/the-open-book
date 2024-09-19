package com.example.the_open_book.email;

import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * EmailSenderService
 */
public interface EmailSenderService {

  public void sendEmail(@NotBlank String from, @NotBlank String to, @NotBlank String subject,
      @NotNull ThymeleafTemplate template,
      Context context)
      throws MessagingException;

  public void sendActivationAccount(String email, String code, String confirmationUrl) ;

}
