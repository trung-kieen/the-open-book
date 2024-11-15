package com.example.the_open_book.service;

import com.example.the_open_book.book.Book;
import com.example.the_open_book.common.PageResult;
import com.example.the_open_book.payload.request.BookRequest;
import com.example.the_open_book.payload.response.BookResponse;
import com.example.the_open_book.payload.response.BorrowBookResponse;
import com.example.the_open_book.user.User;

/**
 * BookService
 */

public interface BookService {

  Book save(BookRequest bookRequest, User currentUser);

  BookResponse getById(Integer bookId);

  PageResult<BookResponse> findAllBook(int page, int size, User user);

  PageResult<BookResponse> findAllBookByOnwer(int page, int size, User user);

  PageResult<BorrowBookResponse> findAllBorrowedBooks(int page, int size, User user);

  PageResult<BorrowBookResponse> findAllReturnedBooks(int page, int size, User user);

  Integer updateBookShareableStatus(int bookId, User user);

  Integer updateBookArchivedStatus(int bookId, User user);

  Long borrowBook(int bookId, User user);

  Long returnBorrowed(int bookId, User user);

  Long returnBookApprove(int bookId, User user);

}
