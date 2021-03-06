package com.github.hugovallada.wallet.service;

import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.entity.WalletItem;
import com.github.hugovallada.wallet.repository.WalletItemRepository;
import com.github.hugovallada.wallet.util.TypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class WalletItemServiceTest {

    @MockBean
    WalletItemRepository repository;

    @Autowired
    WalletItemService service;

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    @Test
    public void testSave() {
        BDDMockito.given(repository.save(Mockito.any(WalletItem.class))).willReturn(getMockWalletItem());

        WalletItem response = service.save(new WalletItem());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getDescription(), DESCRIPTION);
        Assertions.assertEquals(response.getValue().compareTo(VALUE), 0);

    }

    @Test
    public void testFindBetweenDates() {
        List<WalletItem> list = new ArrayList<>();
        list.add(getMockWalletItem());
        Page<WalletItem> page = new PageImpl(list);

        BDDMockito.given(repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class))).willReturn(page);
        Page<WalletItem> response = service.findBetweenDates(1L, new Date(), new Date(), 0);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getContent().size(), 1);
        Assertions.assertEquals(response.getContent().get(0).getDescription(), DESCRIPTION);
    }

    private WalletItem getMockWalletItem() {
        Wallet w = new Wallet();
        w.setId(1L);

        WalletItem wi = new WalletItem(1L, DATE, w, DESCRIPTION, TYPE, VALUE);
        return wi;
    }
}