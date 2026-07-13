package com.margaretnjoki.expense_tracker_api.repository;

import com.margaretnjoki.expense_tracker_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByUserId(UUID userId);
    Optional<Category> findByIdAndUserId(UUID uuid, UUID userId);

    boolean existsByUserIdAndName(UUID userId, String name);
}
