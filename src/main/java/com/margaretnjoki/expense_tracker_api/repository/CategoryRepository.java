package com.margaretnjoki.expense_tracker_api.repository;

import com.margaretnjoki.expense_tracker_api.dto.CategoryTotalResponse;
import com.margaretnjoki.expense_tracker_api.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
//    List<Category> findByUserId(UUID userId);
    Optional<Category> findByIdAndUserId(UUID uuid, UUID userId);

    boolean existsByUserIdAndName(UUID userId, String name);
    Page<Category> findByUserId(UUID userId, Pageable pageable);}

