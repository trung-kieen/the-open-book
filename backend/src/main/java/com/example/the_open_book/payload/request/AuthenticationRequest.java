package com.example.the_open_book.payload.request;

/**
 * AuthenticationRequest
 */
public record AuthenticationRequest(
  String email,
  String password
) {
}
