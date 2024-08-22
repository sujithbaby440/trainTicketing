package com.sujithwork.trainTicketing.service.impl;

import com.sujithwork.trainTicketing.Excepion.TicketNotFoundException;
import com.sujithwork.trainTicketing.Excepion.UserAlreadyRegisteredException;
import com.sujithwork.trainTicketing.entity.Ticket;
import com.sujithwork.trainTicketing.entity.Users;
import com.sujithwork.trainTicketing.repository.TicketRepository;
import com.sujithwork.trainTicketing.repository.UserRepository;
import com.sujithwork.trainTicketing.service.UserService;
import com.sujithwork.trainTicketing.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUtil userUtil;

    public Users saveUser(Users user) {

        if (userUtil.isEmailRegistered(user.getEmail())) {
            log.warn("User already exists {}", user.getEmail());
            throw new UserAlreadyRegisteredException("Email is already registered: " + user.getEmail());
        }
        log.info("User Saved {}", user.getEmail());
        return userRepository.save(user);
    }
    public Users getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new TicketNotFoundException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

}
