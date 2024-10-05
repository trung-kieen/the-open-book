package com.example.the_open_book.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ResgiterDTO
 */
public record RegisterDTO(

    @NotBlank(message = "Firstname must not empty") String firstname,

    @NotBlank(message = "Lastname must not empty") String lastname,

    @NotBlank(message = "Password must not empty") @Size(min = 6, message = "Password must at least 6 character length")
  String password,

    @Email @NotBlank(message = "Email must not empty") String email

) {


}
