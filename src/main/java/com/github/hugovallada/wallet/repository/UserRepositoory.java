package com.github.hugovallada.wallet.repository;

import com.github.hugovallada.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoory extends JpaRepository<User, Long> {
}