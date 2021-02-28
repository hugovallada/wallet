package com.github.hugovallada.wallet.service.impl;

import com.github.hugovallada.wallet.entity.User;
import com.github.hugovallada.wallet.repository.UserRepository;
import com.github.hugovallada.wallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}