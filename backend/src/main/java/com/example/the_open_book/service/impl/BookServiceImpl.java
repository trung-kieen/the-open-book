package com.example.the_open_book.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.the_open_book.book.BookMapper;
import com.example.the_open_book.book.BookRepository;
import com.example.the_open_book.exception.ResourceNotFoundExceeption;
import com.example.the_open_book.payload.request.BookRequest;
import com.example.the_open_book.payload.response.BookResponse;
import com.example.the_open_book.service.BookService;
import com.example.the_open_book.user.User;

import lombok.RequiredArgsConstructor;

/**
 * BookServiceImpl
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  @Override
  public Integer save(BookRequest bookRequest) {
    var currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var book = bookMapper.toEntity(bookRequest, currentUser);
    return bookRepository.save(book).getBookId();
  }

  @Override
  public BookResponse findById(Integer bookId) {
    var book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundExceeption("Not found any book with this id"));
    var resp = bookMapper.toBookResponse(book);
    return  resp;
  }

}
