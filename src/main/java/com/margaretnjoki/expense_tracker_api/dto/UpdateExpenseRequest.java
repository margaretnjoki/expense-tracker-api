package com.margaretnjoki.expense_tracker_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateExpenseRequest( BigDecimal amountKes,
                                    String description,
                                    LocalDate occurredOn,
                                    UUID categoryId) {

}
