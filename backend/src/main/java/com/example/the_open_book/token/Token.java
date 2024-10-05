package com.example.the_open_book.token;

import java.time.LocalDateTime;

import org.springframework.stereotype.Indexed;

import com.example.the_open_book.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Token
 */
@AllArgsConstructor
@Builder
@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Table(name = "tokens")
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "token_id")
  private Long tokenId;

  private String token;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "expired_at")
  private LocalDateTime expiredAt;

  @Column(name = "validated_at")
  private LocalDateTime validatedAt;
  @ManyToOne
  @JoinColumn(name = "user_id",  referencedColumnName = "user_id" ,  nullable = false, updatable = false)
  private User user;
  public boolean isExpired(){
    return LocalDateTime.now().isAfter(expiredAt);
  }

}
