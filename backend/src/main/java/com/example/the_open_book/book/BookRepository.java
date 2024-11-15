package com.example.the_open_book.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.the_open_book.user.User;

/**
 * BookRepository
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

  // Use entity base not navtive queyr like SQL
  // NOTE: :userId must same name so not require to use @Param
  // owner must FetchEager
  @Query("""
      SELECT book FROM Book book
      WHERE  book.archived = false
      AND book.owner.id != :userId
      """)
  Page<Book> findPublicVisible(Pageable pageRequest, Long userId);


}
