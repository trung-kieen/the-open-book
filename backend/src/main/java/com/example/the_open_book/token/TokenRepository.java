package com.example.the_open_book.token;

import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

/**
 * TokenRepository
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
  Optional<Token> findByToken(String tokenCode);

  @Query(nativeQuery = true, value = "SELECT * FROM tokens AS t WHERE user_id = :user_id ORDER BY t.expired_at DESC ")
  Optional<Token> findByUserLastExpired(@Param("user_id") Long  userId);


  @Transactional
  @Modifying
  @Query(nativeQuery =  true , value =  "UPDATE users SET enabled = TRUE  WHERE user_id IN (SELECT user_id FROM tokens WHERE token_id = :token_id)")
  void activateUser(@Param("token_id") Long tokenId);
}
