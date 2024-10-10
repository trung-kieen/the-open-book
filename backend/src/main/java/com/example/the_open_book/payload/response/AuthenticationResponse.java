package com.example.the_open_book.payload.response;

import lombok.Builder;

/**
 * AuthenticationResponse
 */
@Builder
public record AuthenticationResponse(
  String token
) {
}
