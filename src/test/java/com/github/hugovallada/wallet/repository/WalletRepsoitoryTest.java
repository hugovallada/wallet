package com.github.hugovallada.wallet.repository;

import com.github.hugovallada.wallet.entity.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class WalletRepsoitoryTest {
    private static final String NAME = "walletTest";

    @Autowired
    WalletRepository repository;
    

    @Test
    public void testSave() {
        Wallet wallet = new Wallet();
        wallet.setName(NAME);
        wallet.setValue(BigDecimal.valueOf(100.90));

        Wallet savedWallet = repository.save(wallet);

        Assertions.assertNotNull(savedWallet);
    }


}