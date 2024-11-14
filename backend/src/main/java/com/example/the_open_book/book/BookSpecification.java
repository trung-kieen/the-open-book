package com.example.the_open_book.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * BookSpecification
 * Use to specify query without know does user use this predicate or not
 * Contain helper method to pass as Predicate<T> of {@link BookRepository}
 */

public class BookSpecification {
  public static Specification<Book> withOwnerId(Long ownerId) {
    return (root, query, builder) -> builder.equal(root.get("owner").get("userId"), ownerId);
  }

}
