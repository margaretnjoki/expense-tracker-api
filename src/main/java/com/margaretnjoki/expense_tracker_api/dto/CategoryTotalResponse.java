package com.margaretnjoki.expense_tracker_api.dto;

import java.math.BigDecimal;

public record CategoryTotalResponse(String categoryName, BigDecimal total) {
}

