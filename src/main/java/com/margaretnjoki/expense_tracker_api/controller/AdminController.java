package com.margaretnjoki.expense_tracker_api.controller;

import com.margaretnjoki.expense_tracker_api.dto.UserResponse;
import com.margaretnjoki.expense_tracker_api.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserRepository userRepository;


    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<UserResponse> allUsers(){
        return userRepository.findAll().stream().map(UserResponse::from).toList();
    }
}
