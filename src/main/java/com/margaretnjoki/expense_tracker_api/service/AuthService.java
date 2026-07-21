package com.margaretnjoki.expense_tracker_api.service;

import com.margaretnjoki.expense_tracker_api.dto.RegisterRequest;
import com.margaretnjoki.expense_tracker_api.exception.EmailAlreadyInUseException;
import com.margaretnjoki.expense_tracker_api.model.User;
import com.margaretnjoki.expense_tracker_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request){
        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new EmailAlreadyInUseException(request.email());
        }
        User user= User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role("USER")
                .createdAt(Instant.now())
                .build();
        return userRepository.save(user);
    }
}
