package com.example.the_open_book.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.the_open_book.book.Book;
import com.example.the_open_book.book.BookMapper;
import com.example.the_open_book.book.BookRepository;
import com.example.the_open_book.book.BookSpecification;
import com.example.the_open_book.common.PageResult;
import com.example.the_open_book.exception.ApplicationException;
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
  private final BookMapper bookMapper;
  private final BookRepository bookRepository;
  public Book save(BookRequest bookRequest, User currentUser) {
    var book = bookMapper.toEntity(bookRequest, currentUser);
    bookRepository.save(book);
    return book;
  }
  @Override
  public BookResponse getById(Integer bookId) {
    var book  = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundExceeption("Not found book with id " + bookId));
    return bookMapper.toBookResponse(book);
  }
  @Override
  public PageResult<BookResponse> findAllBook(int page, int size, User user) {

    Pageable pageRequest = PageRequest.of(page, size, Sort.by(Direction.ASC, "createdDate"));
    Page<Book> bookpages = bookRepository.findPublicVisible(pageRequest, user.getUserId());
    List<BookResponse> bookResp = bookpages.stream().map(bookMapper::toBookResponse).toList();
    var pageRs = PageResult.fromPage(bookpages, bookResp);

    // var pageRs  = PageResult.<BookResponse>builder()
    // .resutls(bookResp)
    // .totalElement(bookpages.getTotalElements())
    // .totalPage(bookpages.getTotalPages())
    // .currentPage(bookpages.getNumber())
    // .isLast(bookpages.isLast())
    // .isFist(bookpages.isFirst())
    // .isEmpty(bookpages.isEmpty())
    // .build();

    return pageRs;
  }
  @Override
  public PageResult<BookResponse> findAllBookByOnwer(int page, int size, User user) {
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(Direction.ASC, "createdDate"));

    var bookPages= bookRepository.findAll( BookSpecification.withOwnerId(user.getUserId()) ,pageRequest );
    var bookResp = bookPages.stream().map(bookMapper::toBookResponse).toList();

    var pageRs = PageResult.fromPage(bookPages, bookResp);
    return pageRs;
  }





}
