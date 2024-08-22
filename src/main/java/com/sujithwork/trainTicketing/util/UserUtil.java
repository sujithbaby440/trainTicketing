package com.sujithwork.trainTicketing.util;

import com.sujithwork.trainTicketing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    @Autowired
    private UserRepository userRepository;
    public boolean isEmailRegistered(String email) {
        return userRepository.findAll().stream()
                .anyMatch(user -> email.equals(user.getEmail()));
    }
}
