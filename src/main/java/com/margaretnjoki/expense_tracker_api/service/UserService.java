package com.margaretnjoki.expense_tracker_api.service;

import com.margaretnjoki.expense_tracker_api.exception.BadRequestException;
import com.margaretnjoki.expense_tracker_api.exception.ResourceNotFoundException;
import com.margaretnjoki.expense_tracker_api.model.User;
import com.margaretnjoki.expense_tracker_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void deleteUser(UUID userId, String currentUserEmail){
       if (userId.equals(currentUserEmail)){
           throw new BadRequestException("You cannot delete your own account.");
       }
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found", userId));
    userRepository.delete(user);
    }

}
