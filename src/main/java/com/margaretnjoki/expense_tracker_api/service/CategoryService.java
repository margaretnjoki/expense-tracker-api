package com.margaretnjoki.expense_tracker_api.service;

import com.margaretnjoki.expense_tracker_api.dto.CreateCategoryRequest;
import com.margaretnjoki.expense_tracker_api.exception.ResourceNotFoundException;
import com.margaretnjoki.expense_tracker_api.model.Category;
import com.margaretnjoki.expense_tracker_api.model.User;
import com.margaretnjoki.expense_tracker_api.repository.CategoryRepository;
import com.margaretnjoki.expense_tracker_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class CategoryService {
    private static final UUID DEMO_USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
private final CategoryRepository categoryRepository;
private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<Category> findAll(){
        return categoryRepository.findByUserId(DEMO_USER_ID);
    }

    public Category findById(UUID id){
        return categoryRepository.findByIdAndUserId(id,DEMO_USER_ID)
                .orElseThrow(() -> new ResourceNotFoundException("category", id));
    }

    public Category create(CreateCategoryRequest req){
        User user = userRepository.findById(DEMO_USER_ID).orElseThrow();
        Category category= Category.builder()
                .user(user)
                .name(req.name())
                .color(req.color())
                .build();
        return categoryRepository.save(category);
    }

    public void delete(UUID id){
        Category category=findById(id);
        categoryRepository.delete(category);
    }
}
