package com.margaretnjoki.expense_tracker_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateExpenseRequest(
        @NotNull @DecimalMin("0.0") BigDecimal amountKes,
        @Size(max = 255) String description,
        @NotNull LocalDate occurredOn,
        UUID categoryId
        ) {
}
