package com.margaretnjoki.expense_tracker_api.controller;

import com.margaretnjoki.expense_tracker_api.dto.CategoryResponse;
import com.margaretnjoki.expense_tracker_api.dto.CreateCategoryRequest;
import com.margaretnjoki.expense_tracker_api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponse>  list(){
        return service.findAll().stream().map(CategoryResponse ::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest req){
        return CategoryResponse.from(service.create(req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }

}
