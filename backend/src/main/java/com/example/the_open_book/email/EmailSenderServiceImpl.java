package com.example.the_open_book.email;

import java.nio.charset.StandardCharsets;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.the_open_book.exception.ApplicationException;

import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * EmailSenderService
 * Using Thymeleaf as engine to generate content for email sender
 */
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
  private final JavaMailSender javaMailSender;
  private final SpringTemplateEngine templateEngine;
  // TODO
  private int expireActivationMinutes = 15;

  /**
   * @param to       email target send to
   * @param subject  summary email intention
   * @param template <ThymeleafTemplate> Specify enum value from enum class name
   *                 ThymeleafTemplate. Email need to build content base on this
   *                 file so html file name will specify in enum property
   * @param context  use setVariable to set key pair value for Thymeleaf context.
   *                 This action attempt to encapsulate custom variable pass into
   *                 template file.
   * @param from
   * @throws MessagingException
   */
  @Async
  public void sendEmail(@NotBlank String from, @NotBlank String to, @NotBlank String subject,
      @NotNull ThymeleafTemplate template,
      Context context)
      throws MessagingException {
    var mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED,
        StandardCharsets.UTF_8.name());
    final var useAsHTML = true;

    // Process the template with the given context
    String htmlContent = templateEngine.process(template.getTemplateFileHtml(), context);

    // Set email properties
    helper.setTo(to);
    helper.setFrom(from);
    helper.setSubject(subject);
    helper.setText(htmlContent, useAsHTML); // Set true for HTML content

    // Send the email
    javaMailSender.send(mimeMessage);
  }

  public void sendActivationAccount(String email, String code, String confirmationUrl) {
    Context context = new Context();
    context.setVariable("code", code);
    context.setVariable("expireMinutes", expireActivationMinutes);
    // // TODO: builder url
    context.setVariable("confirmationUrl", confirmationUrl);

    // TODO change this
    final String from = "hellokitty@duck.com";
    try {
      sendEmail(from, email, ThymeleafTemplate.ACTIVE_ACCOUNT.getEmailSubject(), ThymeleafTemplate.ACTIVE_ACCOUNT, context);
    } catch (Exception ex) {
      throw new ApplicationException(ex.getMessage());
    }

  }

}
