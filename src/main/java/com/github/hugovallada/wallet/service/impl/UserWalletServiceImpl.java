package com.github.hugovallada.wallet.service.impl;

import com.github.hugovallada.wallet.entity.UserWallet;
import com.github.hugovallada.wallet.repository.UserWalletRepository;
import com.github.hugovallada.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWalletServiceImpl implements UserWalletService {

    private UserWalletRepository repository;

    @Autowired
    public UserWalletServiceImpl(UserWalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserWallet save(UserWallet uWallet) {
        return repository.save(uWallet);
    }
}