package com.margaretnjoki.expense_tracker_api.controller;

import com.margaretnjoki.expense_tracker_api.dto.LoginRequest;
import com.margaretnjoki.expense_tracker_api.dto.LoginResponse;
import com.margaretnjoki.expense_tracker_api.dto.RegisterRequest;
import com.margaretnjoki.expense_tracker_api.dto.UserResponse;
import com.margaretnjoki.expense_tracker_api.security.JwtService;
import com.margaretnjoki.expense_tracker_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request){
        log.info(">>> REGISTER ENDPOINT HIT <<<");

        return UserResponse.from(authService.register(request));

    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        log.info(">>> LOGIN START <<<");


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        String token = jwtService.generateToken(request.email());
        return new LoginResponse(token);
    }

    @GetMapping("/test")
    public String test() {
        return "Auth controller is working!";
    }

}

