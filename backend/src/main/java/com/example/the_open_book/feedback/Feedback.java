package com.example.the_open_book.feedback;

import com.example.the_open_book.book.Book;
import com.example.the_open_book.common.BaseEntity;
import com.example.the_open_book.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Feedback
 */
@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feedback_id", nullable = false)
  private int feedbackId;

  @NotBlank
  @Min(value = 1)
  @Max(value = 5)
  private Double note;   // Rate from 1 - 5

  private String comment;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;


  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;
}
