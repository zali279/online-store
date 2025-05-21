package com.store.demo.service;

import com.store.demo.model.User;
import com.store.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser( User user) {
        return userRepository.save(user);
    }
}
