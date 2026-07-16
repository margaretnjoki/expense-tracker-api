package com.margaretnjoki.expense_tracker_api.repository;

import com.margaretnjoki.expense_tracker_api.dto.CategoryTotalResponse;
import com.margaretnjoki.expense_tracker_api.dto.SummaryReportResponse;
import com.margaretnjoki.expense_tracker_api.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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

@EntityGraph(attributePaths = "category")
    Page<Expense> findByUserId(UUID userId, Pageable pageable);

    @EntityGraph(attributePaths = {"category"})
    Page<Expense> findByUserIdAndOccurredOnBetween(
            UUID userId,
            LocalDate from,
            LocalDate to,
            Pageable pageable
    );

    @Query("""
SELECT new com.margaretnjoki.expense_tracker_api.dto.CategoryTotalResponse(
COALESCE(c.name, 'Uncategorised'), SUM(e.amountKes))
FROM Expense e
LEFT JOIN e.category c
WHERE e.user.id = :userId
AND e.occurredOn BETWEEN :from AND :to
GROUP BY c.name
ORDER BY SUM (e.amountKes) DESC
""")
    List<CategoryTotalResponse> totalByCategory(@Param("userId") UUID userId,
                                                @Param("from")LocalDate from,
                                                @Param("to") LocalDate to);

@Query("SELECT COALESCE(SUM(e.amountKes), 0) FROM Expense e " +
        "WHERE e.user.id = :userId AND e.occurredOn BETWEEN :from AND :to")
  BigDecimal totalBetween(@Param("userId") UUID userId,
                          @Param("from") LocalDate from,
                          @Param("to") LocalDate to);

long countByUserIdAndOccurredOnBetween(UUID userId, LocalDate from, LocalDate to);

    @Query("""
    SELECT e.category.name, SUM(e.amountKes)
    FROM Expense e
    GROUP BY e.category.name
    HAVING SUM(e.amountKes) > :min
""")
    List<Object[]> findCategoriesWithTotalGreaterThan(
            @Param("min") BigDecimal min);

    @Query("""
SELECT new com.margaretnjoki.expense_tracker_api.dto.SummaryReportResponse(
    COALESCE(SUM(e.amountKes), 0.0),
    COUNT(e),
    COALESCE(AVG(e.amountKes), 0.0)
)
FROM Expense e
WHERE e.user.id = :userId
AND e.occurredOn BETWEEN :from AND :to
""")
    SummaryReportResponse getSummaryReport(
            @Param("userId") UUID userId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to);

    }
