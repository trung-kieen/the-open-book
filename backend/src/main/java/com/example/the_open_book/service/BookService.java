package com.example.the_open_book.service;

import com.example.the_open_book.payload.request.BookRequest;
import com.example.the_open_book.payload.response.BookResponse;

/**
 * BookService
 */
public interface BookService {




    /**
     * @param bookRequest
     * @return created book id
     */
    Integer save(BookRequest bookRequest);

    BookResponse findById(Integer bookId);

}
