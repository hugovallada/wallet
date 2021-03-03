package com.github.hugovallada.wallet.repository;

import com.github.hugovallada.wallet.entity.WalletItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {
}