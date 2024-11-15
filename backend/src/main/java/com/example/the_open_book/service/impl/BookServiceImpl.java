package com.example.the_open_book.service.impl;

import java.util.List;

import org.apache.catalina.util.ResourceSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.the_open_book.book.Book;
import com.example.the_open_book.book.BookMapper;
import com.example.the_open_book.book.BookRepository;
import com.example.the_open_book.book.BookSpecification;
import com.example.the_open_book.common.PageResult;
import com.example.the_open_book.exception.ApplicationException;
import com.example.the_open_book.exception.OperationNotPermitedException;
import com.example.the_open_book.exception.ResourceNotFoundExceeption;
import com.example.the_open_book.payload.request.BookRequest;
import com.example.the_open_book.payload.response.BookResponse;
import com.example.the_open_book.payload.response.BorrowBookResponse;
import com.example.the_open_book.service.BookService;
import com.example.the_open_book.transaction.TransactionHistory;
import com.example.the_open_book.transaction.TransactionHistoryRepository;
import com.example.the_open_book.user.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * BookServiceImpl
 */

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  private final BookMapper bookMapper;
  private final BookRepository bookRepository;
  private final TransactionHistoryRepository transactionHistoryRepository;

  public Book save(BookRequest bookRequest, User currentUser) {
    var book = bookMapper.toEntity(bookRequest, currentUser);
    bookRepository.save(book);
    return book;
  }

  @Override
  public BookResponse getById(Integer bookId) {
    var book = getBookOrThrow(bookId);
    return bookMapper.toBookResponse(book);
  }

  @Override
  public PageResult<BookResponse> findAllBook(int page, int size, User user) {

    Pageable pageRequest = PageRequest.of(page, size, Sort.by(Direction.ASC, "createdDate"));
    Page<Book> bookpages = bookRepository.findPublicVisible(pageRequest, user.getUserId());
    List<BookResponse> bookResp = bookpages.stream().map(bookMapper::toBookResponse).toList();
    var pageRs = PageResult.fromPage(bookpages, bookResp);

    return pageRs;
  }

  @Override
  public PageResult<BookResponse> findAllBookByOnwer(int page, int size, User user) {
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(Direction.ASC, "createdDate"));

    var bookPages = bookRepository.findAll(
        BookSpecification.withOwnerId(user.getUserId()), pageRequest);
    var bookResp = bookPages.stream().map(bookMapper::toBookResponse).toList();

    var pageRs = PageResult.fromPage(bookPages, bookResp);
    return pageRs;
  }

  @Override
  public PageResult<BorrowBookResponse> findAllBorrowedBooks(int page, int size, User user) {
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(Direction.ASC, "createdDate"));
    var histories = transactionHistoryRepository.findAllBorrowByUser(pageRequest, user.getUserId());
    var bookResp = histories.stream().map(bookMapper::toBorrowBookResponse).toList();
    var pageRs = PageResult.fromPage(histories, bookResp);
    return pageRs;

  }

  @Override
  public PageResult<BorrowBookResponse> findAllReturnedBooks(int page, int size, User user) {
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(Direction.ASC, "createdDate"));
    var histories = transactionHistoryRepository.findAllReturnedByOwner(pageRequest, user.getUserId());
    var bookResp = histories.stream().map(bookMapper::toBorrowBookResponse).toList();
    var pageRs = PageResult.fromPage(histories, bookResp);
    return pageRs;
  }

  @Override
  public Integer updateBookShareableStatus(int bookId, User user) {
    var book = getBookOrThrow(bookId);
    final boolean currentUserIsOwnThisBook = book.getOwner().getUserId() == user.getUserId();
    if (!currentUserIsOwnThisBook) {
      throw new ApplicationException("User not able to chagne other people book status");
    }
    book.setShareable(!book.getShareable());
    bookRepository.save(book);
    return book.getBookId();

  }

  @Override
  public Integer updateBookArchivedStatus(int bookId, User user) {
    // TODO Auto-generated method stub
    var book = getBookOrThrow(bookId);
    if (user.getUserId() != book.getOwner().getUserId()) {
      throw new ApplicationException("Unable to modify other owner book");

    }
    bookRepository.save(book);
    return book.getBookId();
  }

  @Transactional
  @Override
  public Long borrowBook(int bookId, User user) {
    var book = getBookOrThrow(bookId);

    publicVisibleOrThrow(book);


    final boolean borrowedUserIsOwnThisBook = book.getOwner().getUserId() == user.getUserId();
    if (borrowedUserIsOwnThisBook) {
      throw new OperationNotPermitedException("Why you need to borrow the book you own?");
    }


    boolean isAlreadyBorrowed = transactionHistoryRepository.isAlreadyBorrowedByUser(bookId, user.getUserId());
    if (isAlreadyBorrowed)
      throw new OperationNotPermitedException("Book is not free to borrow");


    var borrowTrans = TransactionHistory.builder()
        .user(user)
        .book(book)
        .returned(false)
        .returnApproved(false)
        .build();
    transactionHistoryRepository.save(borrowTrans);

    return borrowTrans.getTransactionId();
  }

  @Override
  @Transactional
  public Long returnBorrowed(int bookId, User user) {
    var book = getBookOrThrow(bookId);

    publicVisibleOrThrow(book);

    final boolean borrowedUserIsOwnThisBook = book.getOwner().getUserId() == user.getUserId();
    if (borrowedUserIsOwnThisBook) {
      throw new OperationNotPermitedException("Why you need to return the book you own?");
    }

    TransactionHistory trans = transactionHistoryRepository.findByBookIdAndUserId(bookId, user.getUserId()).orElseThrow(() -> new ResourceNotFoundExceeption("No transaction found for book and user"));
    trans.setReturned(true);
    transactionHistoryRepository.save(trans);
    return trans.getTransactionId();
  }
  @Override
  public Long returnBookApprove(int bookId, User user) {
    var book = getBookOrThrow(bookId);

    publicVisibleOrThrow(book);

    final boolean borrowedUserIsOwnThisBook = book.getOwner().getUserId() == user.getUserId();
    if (!borrowedUserIsOwnThisBook) {
      throw new OperationNotPermitedException("You need to own this book to aprrove return");
    }

    TransactionHistory trans = transactionHistoryRepository.findByBookIdAndOwnerId(bookId, user.getUserId()).orElseThrow(() -> new ResourceNotFoundExceeption("No transaction found for book and user"));
    trans.setReturnApproved(true);
    transactionHistoryRepository.save(trans);
    return trans.getTransactionId();
  }

  private void  publicVisibleOrThrow(Book book){
    final boolean isBookAvailablePublic = book.getArchived() || !book.getShareable();
    if (isBookAvailablePublic)
      throw new OperationNotPermitedException("book is not available to borrow");

  }
  private Book getBookOrThrow(int bookId) {
    return bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundExceeption("Book is not aviable" + bookId));

  }

}
