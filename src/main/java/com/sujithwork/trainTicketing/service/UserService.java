package com.sujithwork.trainTicketing.service;

import com.sujithwork.trainTicketing.entity.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Users saveUser(Users user);
    Users getUserById(Long id);
    void deleteUser(Long id);
    Users updateUser(Users user);
}
