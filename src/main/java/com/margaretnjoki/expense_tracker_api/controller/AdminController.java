package com.margaretnjoki.expense_tracker_api.controller;

import com.margaretnjoki.expense_tracker_api.dto.UserResponse;
import com.margaretnjoki.expense_tracker_api.repository.UserRepository;
import com.margaretnjoki.expense_tracker_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserRepository userRepository;
    private final UserService userService;


    public AdminController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponse> allUsers(){
        return userRepository.findAll().stream().map(UserResponse::from).toList();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id, Authentication authentication){
        userService.deleteUser(id, authentication.getName());
        return ResponseEntity.noContent().build();

    }
}
