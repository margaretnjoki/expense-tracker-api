package com.margaretnjoki.expense_tracker_api.service;

import com.margaretnjoki.expense_tracker_api.dto.CategoryTotalResponse;
import com.margaretnjoki.expense_tracker_api.dto.MonthlyReportResponse;
import com.margaretnjoki.expense_tracker_api.exception.BadRequestException;
import com.margaretnjoki.expense_tracker_api.repository.CategoryRepository;
import com.margaretnjoki.expense_tracker_api.repository.ExpenseRepository;
import com.margaretnjoki.expense_tracker_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Service
public class ReportService {
    private static final UUID DEMO_USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ReportService(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public MonthlyReportResponse monthlyReport(int year, int month) {
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to = from.withDayOfMonth(from.lengthOfMonth());
        BigDecimal total = expenseRepository.totalBetween(DEMO_USER_ID, from, to);
        Long count = expenseRepository.countByUserIdAndOccurredOnBetween(DEMO_USER_ID, from, to);
        return new MonthlyReportResponse(year, month, total, count);
    }
    public List<CategoryTotalResponse> totalByCategory(LocalDate from, LocalDate to){
       if (from.isAfter(to)){
           throw new BadRequestException("'from' must not be after 'to'");
       }
       return expenseRepository.totalByCategory(DEMO_USER_ID, from, to);
    }
}
