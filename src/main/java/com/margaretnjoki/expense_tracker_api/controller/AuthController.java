package com.margaretnjoki.expense_tracker_api.controller;

import com.margaretnjoki.expense_tracker_api.dto.RegisterRequest;
import com.margaretnjoki.expense_tracker_api.dto.UserResponse;
import com.margaretnjoki.expense_tracker_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request){
        System.out.println(">>> REGISTER ENDPOINT HIT <<<");

        return UserResponse.from(authService.register(request));

    }
}

