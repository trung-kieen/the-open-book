package com.example.the_open_book.transaction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

/**
 * TransactionHistoryRepository
 */
@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

  @Query("""
      SELECT h FROM TransactionHistory h
      WHERE  h.user.userId = :userId
      """)
  Page<TransactionHistory> findAllBorrowByUser(Pageable pageRequest, Long userId);

  @Query("""
      SELECT h FROM TransactionHistory h
      WHERE  h.book.owner.userId = :userId
      AND returned = true
      """)
  Page<TransactionHistory> findAllReturnedByOwner(Pageable pageRequest, Long userId);

  @Query("""
      SELECT (COUNT(t) >  0)
      FROM TransactionHistory t
      WHERE t.user.userId = :userId
      AND t.book.bookId = :bookId
      AND returned = False
      AND returnApproved = False
      """)
  boolean isAlreadyBorrowedByUser(int bookId, Long userId);

  /**
   * Find book transaction return that borrow by a specific user that have not return yet
   * @param bookId
   * @param userId
   * @return
   */
  @Query("""
      SELECT t FROM TransactionHistory t
      WHERE t.book.bookId = :bookId
      AND t.user.userId  = :userId
      AND t.returned = false
      AND t.returnApproved = false
      """)
  Optional<TransactionHistory> findByBookIdAndUserId(int bookId, Long userId);

  /**
   * Return book that own by a specific user and waiting to return approve
   * @param bookId
   * @param userId
   * @return
   */
  @Query("""
      SELECT t FROM TransactionHistory t
      WHERE t.book.bookId = :bookId
      AND t.book.owner.userId  = :userId
      AND t.returned = true
      AND t.returnApproved = false
      """)
  Optional<TransactionHistory> findByBookIdAndOwnerId(int bookId, Long userId);


}
