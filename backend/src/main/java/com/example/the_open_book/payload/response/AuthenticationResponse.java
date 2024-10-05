package com.example.the_open_book.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * AuthenticationResponse
 */
@Builder
public record AuthenticationResponse(
  String token
) {
}
