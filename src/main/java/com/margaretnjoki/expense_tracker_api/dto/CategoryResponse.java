package com.margaretnjoki.expense_tracker_api.dto;

import com.margaretnjoki.expense_tracker_api.model.Category;

import java.util.UUID;

public record CategoryResponse(UUID id, String name,String color) {
    public static CategoryResponse from (Category c){
        return new CategoryResponse(c.getId(), c.getName(), c.getColor());
    }
}
