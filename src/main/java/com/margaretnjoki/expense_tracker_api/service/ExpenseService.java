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
import com.margaretnjoki.expense_tracker_api.security.CurrentUserProvider;
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

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CurrentUserProvider currentUserProvider;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, UserRepository userRepository, CurrentUserProvider currentUserProvider) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public List<Expense> findAll() {
        UUID userId = currentUserProvider.getCurrentUser().getId();
        return expenseRepository.findAllWithCategoryByUserId(userId);
    }

    public PagedResponse<ExpenseResponse> findAll(
            LocalDate from,
            LocalDate to,
            Pageable pageable
    ) {

        Page<Expense> page;
        UUID userId = currentUserProvider.getCurrentUser().getId();


        if (from != null && to != null) {

            page = expenseRepository.findByUserIdAndOccurredOnBetween(
                    userId,
                    from,
                    to,
                    pageable
            );

        } else {

            page = expenseRepository.findByUserId(
                    userId,
                    pageable
            );
        }

        return PagedResponse.from(page, ExpenseResponse::from);
    }

    public Expense create(CreateExpenseRequest req) {
        UUID userId = currentUserProvider.getCurrentUser().getId();

        User user = userRepository.findById(userId).orElseThrow();
        Category category = null;
        if (req.categoryId() != null) {
            category = categoryRepository.findByIdAndUserId(req.categoryId(), userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", req.categoryId()));
        }

        Expense expense = Expense.builder().user(user).category(category).amountKes(req.amountKes()).description(req.description()).occurredOn(req.occurredOn()).createdAt(Instant.now()).build();
        return expenseRepository.save(expense);
    }

    public List<Expense> findExpensesBetween(LocalDate from, LocalDate to) {
        UUID userId = currentUserProvider.getCurrentUser().getId();

        return expenseRepository.findByUserIdAndOccurredOnBetween(userId, from, to);
    }

    public BigDecimal getTotalSpent() {
        UUID userId = currentUserProvider.getCurrentUser().getId();
        return expenseRepository.totalSpentByUser(userId);
    }

    private Expense findOwnedById(UUID id){
        UUID userId = currentUserProvider.getCurrentUser().getId();
        return expenseRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));
    }

    public Expense findById(UUID id) {
        return findOwnedById(id);
    }

    public Expense update(UUID id, UpdateExpenseRequest req) {
        UUID userId = currentUserProvider.getCurrentUser().getId();
        Expense expense = findOwnedById(id);

        Category category = null;

        if (req.categoryId() != null) {
            category = categoryRepository
                    .findByIdAndUserId(req.categoryId(), userId)
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
        UUID userId = currentUserProvider.getCurrentUser().getId();
        Expense expense = findOwnedById(id);
        expenseRepository.delete(expense);
    }

    public List<Object[]> findCategoriesWithTotalGreaterThan(BigDecimal min) {

        return expenseRepository.findCategoriesWithTotalGreaterThan(min);
    }

    public SummaryReportResponse getSummaryReport(
            LocalDate from,
            LocalDate to) {
        UUID userId = currentUserProvider.getCurrentUser().getId();


        return expenseRepository.getSummaryReport(userId,
                from,
                to
        );
    }

}
