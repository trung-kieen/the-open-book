package com.example.the_open_book.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TokenRepository
 */
@Repository
public interface  TokenRepository extends JpaRepository<Token, Long>  {
  Optional<Token> findByToken(String tokenCode);

}
