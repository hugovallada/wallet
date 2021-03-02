package com.github.hugovallada.wallet.repository;

import com.github.hugovallada.wallet.entity.User;
import com.github.hugovallada.wallet.entity.UserWallet;
import com.github.hugovallada.wallet.entity.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class UserWalletRepositoryTest {

    @Autowired
    UserWalletRepository repository;

    @Autowired
    UserRepository uRepository;

    @Autowired
    WalletRepository wRepository;

    @BeforeEach
    void setUp() {
        User u = new User();
        u.setName("Test");
        u.setEmail("test@gmail.com");
        u.setPassword("123456");

        uRepository.save(u);

        Wallet w = new Wallet();
        w.setName("Test");
        w.setValue(BigDecimal.valueOf(190.20));

        wRepository.save(w);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testSave() {
        UserWallet uWallet = new UserWallet();

        User user = uRepository.getOne(1L);
        Wallet wallet = wRepository.getOne(1L);

        uWallet.setUsers(user);
        uWallet.setWallet(wallet);

        UserWallet response = repository.save(uWallet);

        Assertions.assertNotNull(response);

    }
}