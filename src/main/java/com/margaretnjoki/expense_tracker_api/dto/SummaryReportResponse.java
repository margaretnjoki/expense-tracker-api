package com.margaretnjoki.expense_tracker_api.dto;

import java.math.BigDecimal;

public record SummaryReportResponse(
        BigDecimal totalSpend,
        Long expenseCount,
        Double averageExpense
) {}
