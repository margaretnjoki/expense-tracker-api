package com.margaretnjoki.expense_tracker_api.dto;

import com.margaretnjoki.expense_tracker_api.model.User;

import java.util.UUID;

public record UserResponse(UUID id, String email,String role) {
    public static UserResponse from (User u){
        return new UserResponse(u.getId(), u.getEmail(), u.getRole());
    }
}
