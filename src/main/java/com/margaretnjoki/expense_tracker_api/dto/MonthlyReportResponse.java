package com.margaretnjoki.expense_tracker_api.dto;

import java.math.BigDecimal;

public record MonthlyReportResponse(int year, int month, BigDecimal total, long count) {
}
