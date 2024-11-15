package com.example.the_open_book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.the_open_book.book.Book;
import com.example.the_open_book.common.PageResult;
import com.example.the_open_book.payload.request.BookRequest;
import com.example.the_open_book.payload.response.BookResponse;
import com.example.the_open_book.payload.response.BorrowBookResponse;
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

  /**
   * TODO: Check if user is own this book or this book is public
   * Any private book should not display to other people
   * @param bookId
   * @return
   */
  @GetMapping("/{book-id}")
  public ResponseEntity<?> getById(@PathVariable("book-id") Integer bookId) {
    BookResponse book = bookService.getById(bookId);
    return ResponseEntity.ok(book);
  }

  /**
   * Get the all public book from other user. This action intend not display any
   * book that own by current user
   *
   * @param page
   * @param size
   * @return
   */
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

  /**
   * Find all book that own by current user
   */
  @GetMapping("/owner")
  public ResponseEntity<PageResult<BookResponse>> findByOwnerByCurrentUser(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    var user = getCurrentUser();
    var pageRs = bookService.findAllBookByOnwer(page, size, user);
    return ResponseEntity.ok(pageRs);
  }

  /**
   * Find all borrow book by current user
   *
   * @param page
   * @param size
   * @return
   */
  @GetMapping("/borrowed")
  public ResponseEntity<PageResult<BorrowBookResponse>> findAllBorrowedBooks(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    var user = getCurrentUser();
    return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, user));
  }

  /**
   *
   * Return all book belong to current user that have been return in transaction
   * history
   *
   * @param page
   * @param size
   * @return
   */
  @GetMapping("/returned")
  public ResponseEntity<PageResult<BorrowBookResponse>> findAllReturnedBooks(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    var user = getCurrentUser();
    return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, user));
  }


  @PatchMapping("/shareable/{book-id}")
  public ResponseEntity<?> updateBookShareableStatus(@PathVariable("book-id") int bookId) {
    var user = getCurrentUser();
    var book = bookService.updateBookShareableStatus(bookId, user);

    return ResponseEntity.ok(book);

  }

  @PatchMapping("/archived/{book-id}")
  public ResponseEntity<?> updateBookArchivedStatus(@PathVariable("book-id") int bookId) {
    var user = getCurrentUser();
    return ResponseEntity.ok(bookService.updateBookArchivedStatus(bookId, user));

  }

  /**
   * @param bookId from class path
   * @return transaction id that created
   */
  @PostMapping("/borrowed/{book-id}")
  public ResponseEntity<?> borrowBook(@PathVariable("book-id") int bookId) {
    var user = getCurrentUser();
    return ResponseEntity.ok(bookService.borrowBook(bookId, user));

  }

  @GetMapping("/returned/{book-id}")
  public ResponseEntity<?> returnBorrowedBook(@PathVariable("book-id") int bookId){
    var user = getCurrentUser();
    return ResponseEntity.ok(bookService.returnBorrowed(bookId, user));
  }
  @GetMapping("/returned/approve/{book-id}")
  public ResponseEntity<?> returnBookApprove(@PathVariable("book-id") int bookId){
    var user = getCurrentUser();
    return ResponseEntity.ok(bookService.returnBookApprove(bookId, user));
  }


  private User getCurrentUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

}
