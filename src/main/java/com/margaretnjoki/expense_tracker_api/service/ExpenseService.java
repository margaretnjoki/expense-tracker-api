package com.margaretnjoki.expense_tracker_api.service;

import com.margaretnjoki.expense_tracker_api.ExpenseTrackerApiApplication;
import com.margaretnjoki.expense_tracker_api.dto.*;
import com.margaretnjoki.expense_tracker_api.exception.ResourceNotFoundException;
import com.margaretnjoki.expense_tracker_api.model.Category;
import com.margaretnjoki.expense_tracker_api.model.Expense;
import com.margaretnjoki.expense_tracker_api.model.User;
import com.margaretnjoki.expense_tracker_api.repository.CategoryRepository;
import com.margaretnjoki.expense_tracker_api.repository.ExpenseRepository;
import com.margaretnjoki.expense_tracker_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {
    private static final UUID DEMO_USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<Expense> findAll() {
        return expenseRepository.findAllWithCategoryByUserId(DEMO_USER_ID);
    }

    public PagedResponse<ExpenseResponse> findAll(
            LocalDate from,
            LocalDate to,
            Pageable pageable
    ) {

        Page<Expense> page;

        if (from != null && to != null) {

            page = expenseRepository.findByUserIdAndOccurredOnBetween(
                    DEMO_USER_ID,
                    from,
                    to,
                    pageable
            );

        } else {

            page = expenseRepository.findByUserId(
                    DEMO_USER_ID,
                    pageable
            );
        }

        return PagedResponse.from(page, ExpenseResponse::from);
    }
    public Expense create(CreateExpenseRequest req) {
        User user = userRepository.findById(DEMO_USER_ID).orElseThrow();
        Category category = null;
        if (req.categoryId() != null) {
            category = categoryRepository.findByIdAndUserId(req.categoryId(), DEMO_USER_ID)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", req.categoryId()));
        }

        Expense expense = Expense.builder().user(user).category(category).amountKes(req.amountKes()).description(req.description()).occurredOn(req.occurredOn()).createdAt(Instant.now()).build();
        return expenseRepository.save(expense);
    }

    public List<Expense> findExpensesBetween( LocalDate from, LocalDate to){
       return expenseRepository.findByUserIdAndOccurredOnBetween(DEMO_USER_ID, from, to);
    }

    public BigDecimal getTotalSpent() {
        return expenseRepository.totalSpentByUser(DEMO_USER_ID);
    }

    public Expense findById(UUID id) {
        return expenseRepository
                .findByIdAndUserId(id, DEMO_USER_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));
    }

    public Expense update(UUID id, UpdateExpenseRequest req) {

        Expense expense = expenseRepository
                .findByIdAndUserId(id, DEMO_USER_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));

        Category category = null;

        if (req.categoryId() != null) {
            category = categoryRepository
                    .findByIdAndUserId(req.categoryId(), DEMO_USER_ID)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Category", req.categoryId()));
        }

        expense.setAmountKes(req.amountKes());
        expense.setDescription(req.description());
        expense.setOccurredOn(req.occurredOn());
        expense.setCategory(category);

        return expenseRepository.save(expense);
    }

    public void delete(UUID id) {

        Expense expense = expenseRepository
                .findByIdAndUserId(id, DEMO_USER_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));

        expenseRepository.delete(expense);
    }

    public List<Object[]> findCategoriesWithTotalGreaterThan(BigDecimal min) {
        return expenseRepository.findCategoriesWithTotalGreaterThan(min);
    }

    public SummaryReportResponse getSummaryReport(
            LocalDate from,
            LocalDate to) {

        return expenseRepository.getSummaryReport(DEMO_USER_ID,
                from,
                to
        );
    }

}
