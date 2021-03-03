package com.github.hugovallada.wallet.repository;

import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.entity.WalletItem;
import com.github.hugovallada.wallet.util.TypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
public class WalletItemRepositoryTest {

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    @Autowired
    private WalletItemRepository repository;

    @Autowired
    private WalletRepository walletRepository;


    @Test
    public void testSave() {

        Wallet w = new Wallet();
        w.setName("WalletTest");
        w.setValue(BigDecimal.valueOf(500));

        walletRepository.save(w);

        WalletItem walletItem = new WalletItem(null, DATE, w, DESCRIPTION, TYPE, VALUE);

        WalletItem response = repository.save(walletItem);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getWallet(), w);
        Assertions.assertEquals(response.getDescription(), DESCRIPTION);
        Assertions.assertEquals(response.getType(), TYPE);
        Assertions.assertEquals(response.getValue(), VALUE);
    }

}