package com.example.the_open_book.payload.response;

import java.util.List;

import com.example.the_open_book.token.history_transaction.TransactionHistory;
import com.example.the_open_book.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BookResponse
 */

/**
 * BookResponse
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {


private   int bookId;
private   String title;
private   String authorName;
private   String isbn;
private   String synopsis;
private   String bookCover;
private   boolean  archived;
private   boolean  shareable;
private   List<TransactionHistory> histories;
private   User owner;
private Double rate;

}
