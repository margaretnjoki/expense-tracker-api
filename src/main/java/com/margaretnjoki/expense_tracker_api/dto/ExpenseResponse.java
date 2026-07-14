package com.margaretnjoki.expense_tracker_api.dto;

import com.margaretnjoki.expense_tracker_api.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponse(UUID id, BigDecimal amountKes, String description, LocalDate occurredOn,String categoryName) {
    public static ExpenseResponse from(Expense e){
        String cat=(e.getCategory() != null) ? e.getCategory().getName() : null;
        return new ExpenseResponse(e.getId(),e.getAmountKes(),e.getDescription(),e.getOccurredOn(), cat);
    }
}
