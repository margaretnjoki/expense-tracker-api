package com.margaretnjoki.expense_tracker_api.service;

import com.margaretnjoki.expense_tracker_api.dto.CategoryResponse;
import com.margaretnjoki.expense_tracker_api.dto.CreateCategoryRequest;
import com.margaretnjoki.expense_tracker_api.dto.PagedResponse;
import com.margaretnjoki.expense_tracker_api.exception.ResourceNotFoundException;
import com.margaretnjoki.expense_tracker_api.model.Category;
import com.margaretnjoki.expense_tracker_api.model.User;
import com.margaretnjoki.expense_tracker_api.repository.CategoryRepository;
import com.margaretnjoki.expense_tracker_api.repository.UserRepository;
import com.margaretnjoki.expense_tracker_api.security.CurrentUserProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CategoryService {
private final CategoryRepository categoryRepository;
private final UserRepository userRepository;
private final CurrentUserProvider currentUserProvider;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository, CurrentUserProvider currentUserProvider) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.currentUserProvider = currentUserProvider;
    }
private Category findOwnedById(UUID id){
        UUID userId = currentUserProvider.getCurrentUser().getId();
        return categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("category", id));

}
    public PagedResponse<CategoryResponse> findAll(Pageable pageable) {
        log.info("get all categories");
        UUID userId= currentUserProvider.getCurrentUser().getId();
        Page<Category> page = categoryRepository.findByUserId(
                userId,
                pageable
        );

        return PagedResponse.from(page, CategoryResponse::from);
    }
    public Category findById(UUID id){
        UUID userId= currentUserProvider.getCurrentUser().getId();
        return findOwnedById(id);
    }

    public Category create(CreateCategoryRequest req){
        UUID userId= currentUserProvider.getCurrentUser().getId();
        User user = userRepository.findById(userId).orElseThrow();
        Category category= Category.builder()
                .user(user)
                .name(req.name())
                .color(req.color())
                .build();
        return categoryRepository.save(category);
    }

    public void delete(UUID id){
        Category category=findOwnedById(id);
        categoryRepository.delete(category);
    }
}
