package com.margaretnjoki.expense_tracker_api.repository;

import com.margaretnjoki.expense_tracker_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
