package com.example.the_open_book.common;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.the_open_book.payload.response.BookResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PageResult
 */
// TODO
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
  private List<T> resutls;
  private Long totalElement;
  private Integer totalPage;
  private Integer currentPage;
  private boolean isLast;
  private boolean isFist;
  private boolean isEmpty;

  public static <V> PageResult<V> fromPage(Page<?> page, List<V> resultSet) {

    return PageResult.<V>builder()
        .resutls(resultSet)
        .totalElement(page.getTotalElements())
        .totalPage(page.getTotalPages())
        .currentPage(page.getNumber())
        .isLast(page.isLast())
        .isFist(page.isFirst())
        .isEmpty(page.isEmpty())
        .build();

  }
}
