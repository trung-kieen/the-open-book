package com.example.the_open_book.token;

import java.util.Optional;

/**
 * tokenService
 */
public interface TokenService {

  Optional<Token> findByToken(String tokenCode);
  /**
   * Check if current user have any token that is not expired yet
   */
  void activeUserMatchToken(Token token );

  Token save(Token token);


}
