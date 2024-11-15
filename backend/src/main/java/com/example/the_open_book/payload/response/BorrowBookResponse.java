package com.example.the_open_book.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BorrowBookResponse
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowBookResponse {

  private int bookId;

  private String title;
  private String authorName;

  private String isbn;
  private  double rate;
  private boolean  returned;
  private boolean  shareable;
  private boolean  returnApproved;
}
