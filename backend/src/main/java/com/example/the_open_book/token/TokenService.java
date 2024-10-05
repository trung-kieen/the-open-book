package com.example.the_open_book.token;

import java.util.Optional;

import com.example.the_open_book.user.User;

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
