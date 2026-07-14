package com.margaretnjoki.expense_tracker_api.repository;

import com.margaretnjoki.expense_tracker_api.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    Optional<Expense> findByIdAndUserId(UUID id, UUID userId);
List<Expense> findByUserId(UUID userId);
List<Expense> findByUserIdAndOccurredOnBetween(UUID userId, LocalDate from,LocalDate to);
@Query("SELECT COALESCE(SUM(e.amountKes), 0) FROM Expense e WHERE e.user.id = :userId")
    BigDecimal totalSpentByUser(@Param("userId") UUID userId);

@Query("SELECT e FROM Expense e LEFT JOIN FETCH e.category WHERE e.user.id = :userId")
  List<Expense> findAllWithCategoryByUserId(@Param("userId") UUID userId);



}
