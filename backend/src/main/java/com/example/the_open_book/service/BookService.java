package com.example.the_open_book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.the_open_book.book.Book;
import com.example.the_open_book.common.PageResult;
import com.example.the_open_book.payload.request.BookRequest;
import com.example.the_open_book.payload.response.BookResponse;
import com.example.the_open_book.user.User;

/**
 * BookService
 */

public interface BookService {

  Book save(BookRequest bookRequest, User currentUser);

  BookResponse getById(Integer bookId);

  PageResult<BookResponse> findAllBook(int page, int size, User user);

  PageResult<BookResponse> findAllBookByOnwer(int page, int size, User user);




}
