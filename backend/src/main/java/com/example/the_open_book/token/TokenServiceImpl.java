package com.example.the_open_book.token;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * TokenServiceImpl
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  private final  TokenRepository tokenRepository;

  @Override
  public Optional<Token> findByToken(String tokenCode) {
    return tokenRepository.findByToken(tokenCode);
  }


  @Override
  public Token save(Token token) {
    return tokenRepository.save(token);
  }


  @Override
  public void activeUserMatchToken(Token token) {
    tokenRepository.activateUser(token.getTokenId());
  }




}
