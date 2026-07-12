package com.margaretnjoki.expense_tracker_api.repository;

import com.margaretnjoki.expense_tracker_api.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

}
