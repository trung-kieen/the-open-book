package com.example.the_open_book.token.history_transaction;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * BookTransactionHistory
 */

@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionHistory extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private Long transactionId;

  private boolean returned;

  private boolean returnApproved;

  /**
   * Many to many relationship have attribute must define as new entity for this
   * attribute. We use @JoinColumn to rename for FK column name
   */
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;


  // private Book book;
  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

}
