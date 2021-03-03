package com.github.hugovallada.wallet.repository;

import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.entity.WalletItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
public class WalletItemRepositoryTest {

    private static final Date DATE = new Date();
    private static final String TYPE = "ENTRADA";
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    @Autowired
    private WalletItemRepository repository;

    @Test
    public void testSave() {

        Wallet w = new Wallet();
        w.setName("WalletTest");
        w.setValue(BigDecimal.valueOf(500));

        WalletItem walletItem = new WalletItem(null, DATE, w, DESCRIPTION, TYPE, VALUE);

        WalletItem response = repository.save(walletItem);

        Assertions.assertNotNull(response);
    }

}