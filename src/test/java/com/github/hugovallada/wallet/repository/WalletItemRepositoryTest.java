package com.github.hugovallada.wallet.repository;

import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.entity.WalletItem;
import com.github.hugovallada.wallet.util.TypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class WalletItemRepositoryTest {

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    private Long savedWalletItemId = null;
    private Long savedWalletId = null;

    @Autowired
    private WalletItemRepository repository;

    @Autowired
    private WalletRepository walletRepository;


    @BeforeEach
    void setUp() {
        Wallet w = new Wallet();
        w.setName("Carteira Test");
        w.setValue(BigDecimal.valueOf(250));
        walletRepository.save(w);

        WalletItem wi = new WalletItem(null, DATE, w, DESCRIPTION, TYPE, VALUE);
        repository.save(wi);

        savedWalletItemId = wi.getId();
        savedWalletId = w.getId();

    }

//    @AfterEach
//    void tearDown() {
//        repository.deleteAll();
//        walletRepository.deleteAll();
//    }

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

    @Test
    public void testUpdate() {
        Optional<WalletItem> w1 = repository.findById(savedWalletItemId);

        String description = "Descricao alterada";

        WalletItem changed = w1.get();
        changed.setDescription(description);

        repository.save(changed);

        Optional<WalletItem> newWalletItem = repository.findById(savedWalletItemId);

        Assertions.assertEquals(description, newWalletItem.get().getDescription());

    }

    @Test
    public void deleteItem() {
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        WalletItem wi = new WalletItem(null, DATE, wallet.get(), DESCRIPTION, TYPE, VALUE);

        repository.save(wi);
        repository.deleteById(wi.getId());

        Optional<WalletItem> response = repository.findById(wi.getId());

        Assertions.assertFalse(response.isPresent());

    }

    @Test
    public void testFindBetweenDates() {
        Optional<Wallet> w = walletRepository.findById(savedWalletId);

        LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
        Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

        repository.save(new WalletItem(null, currentDatePlusFiveDays, w.get(), DESCRIPTION, TYPE, VALUE));
        repository.save(new WalletItem(null, currentDatePlusSevenDays, w.get(), DESCRIPTION, TYPE, VALUE));

        PageRequest pg = PageRequest.of(0, 10);
        Page<WalletItem> response = repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(savedWalletId, DATE, currentDatePlusFiveDays, pg);

        Assertions.assertEquals(response.getContent().size(), 2);
        Assertions.assertEquals(response.getTotalElements(), 2);
        Assertions.assertEquals(response.getContent().get(0).getWallet().getId(), savedWalletId);


    }

    @Test
    public void testFindByType() {
        List<WalletItem> response = repository.findByWalletIdAndType(savedWalletId, TYPE);

        Assertions.assertEquals(response.size(), 1);
        Assertions.assertEquals(response.get(0).getType(), TYPE);
    }

    @Test
    public void testFindByTypeSD() {
        Optional<Wallet> w = walletRepository.findById(savedWalletId);

        repository.save(new WalletItem(null, DATE, w.get(), DESCRIPTION, TypeEnum.SD, VALUE));

        List<WalletItem> response = repository.findByWalletIdAndType(savedWalletId, TypeEnum.SD);

        Assertions.assertEquals(response.size(), 1);
        Assertions.assertEquals(response.get(0).getType(), TypeEnum.SD);
    }

    @Test
    public void testSumByWallet() {
        Optional<Wallet> w = walletRepository.findById(savedWalletId);
        repository.save(new WalletItem(null, DATE, w.get(), DESCRIPTION, TYPE, BigDecimal.valueOf(150.80)));
        BigDecimal response = repository.sumByWalletId(savedWalletId);

        Assertions.assertEquals(response.compareTo(BigDecimal.valueOf(215.8)), 0);
    }

}