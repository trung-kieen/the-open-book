package com.example.the_open_book.book;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.the_open_book.payload.request.BookRequest;
import com.example.the_open_book.payload.response.BookResponse;
import com.example.the_open_book.user.User;

import lombok.RequiredArgsConstructor;

/**
 * BookMapper
 */
@Service
@RequiredArgsConstructor
public class BookMapper {
  public final ModelMapper modelMapper;


  public Book toEntity(BookRequest bookRequest) {
    var book = modelMapper.map(bookRequest, Book.class);
    return book;
  }

  public Book toEntity(BookRequest bookRequest, User owner) {
    var book = modelMapper.map(bookRequest, Book.class);
    book.setOwner(owner);
    return book;
  }

  public BookResponse toBookResponse(Book book){
    var bookres = new BookResponse();
    bookres.setIsbn(book.getIsbn());
    bookres.setBookId(book.getBookId());
    bookres.setTitle(book.getTitle());
    bookres.setAuthorName(book.getAuthorName());
    bookres.setSynopsis(book.getSynopsis());
    bookres.setArchived(book.getArchived());
    bookres.setShareable(book.getShareable());
    var owner  = book.getOwner();
    bookres.setOwner(owner.getFullname());
    // TODO
    // bookres.setBookCover(book.getBookCover());
    return bookres;
  }

}
