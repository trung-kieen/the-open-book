
package com.example.the_open_book.book;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id", nullable = false)
  private int courseId;
  // TODO

  @Column(name = "title", nullable = true, length = 50)
  private String title;

  @Column(name = "author_name")
  private String authorName;

  private String isbn;

  private String synopsis;

  private String bookCover;

  @Column(columnDefinition = "boolean default false")
  private boolean  archived;

  @Column(columnDefinition = "boolean default false")
  private boolean  shareable;


}
