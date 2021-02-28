package com.github.hugovallada.wallet.service;

import com.github.hugovallada.wallet.entity.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findByEmail(String email);

}