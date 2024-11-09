package com.example.the_open_book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.the_open_book.payload.request.BookRequest;
import com.example.the_open_book.service.BookService;

import lombok.RequiredArgsConstructor;

/**
 * BookController
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {
  private final BookService bookService;

  @PostMapping
  public ResponseEntity<?> save(BookRequest bookRequest) {
    return ResponseEntity.ok(bookService.save(bookRequest));
  }

  @GetMapping("{id}")
  public ResponseEntity<?> findById(@PathVariable("id") Integer bookId){
    return ResponseEntity.ok(bookService.findById(bookId));
  }

}
