package com.github.hugovallada.wallet.repository;

import com.github.hugovallada.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}