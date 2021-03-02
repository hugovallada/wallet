package com.github.hugovallada.wallet.service.impl;

import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.repository.WalletRepository;
import com.github.hugovallada.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository repository;

    @Autowired
    public WalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return repository.save(wallet);
    }
}