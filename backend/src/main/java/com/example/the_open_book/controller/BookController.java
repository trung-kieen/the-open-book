package com.example.the_open_book.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.the_open_book.book.Book;
import com.example.the_open_book.common.PageResult;
import com.example.the_open_book.payload.request.BookRequest;
import com.example.the_open_book.payload.response.BookResponse;
import com.example.the_open_book.service.BookService;
import com.example.the_open_book.user.User;

import lombok.RequiredArgsConstructor;

/**
 * BookController
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {
  private final BookService bookService;

  /**
   * @param bookRequest
   * @return Book id that saved
   */
  @PostMapping
  public ResponseEntity<?> save(BookRequest bookRequest) {
    User currentUser = getCurrentUser();
    Book book = bookService.save(bookRequest, currentUser);
    return ResponseEntity.ok(book.getBookId());
  }

  @GetMapping("/{book-id}")
  public ResponseEntity<?> getById(@PathVariable("book-id") Integer bookId) {
    BookResponse book = bookService.getById(bookId);
    return ResponseEntity.ok(book);
  }

  @GetMapping
  public ResponseEntity<PageResult<BookResponse>> getAllByPage(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {

    var user = getCurrentUser();
    // TODO Sort by createdDate
    // Use create paginate condition
    // Use any repository method impl with paginate as input still work well
    PageResult<BookResponse> books = bookService.findAllBook(page, size, user);

    return ResponseEntity.ok(books);
  }

  @GetMapping("/owner")
  public ResponseEntity<PageResult<BookResponse>> findByOwnerByCurrentUser(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    var user = getCurrentUser();
    var pageRs = bookService.findAllBookByOnwer(page, size, user);
    return ResponseEntity.ok(pageRs);

  }

  private User getCurrentUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

}
