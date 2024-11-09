package com.example.the_open_book.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BookRepository
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{


}
