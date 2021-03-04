package com.github.hugovallada.wallet.service;

import com.github.hugovallada.wallet.entity.WalletItem;
import com.github.hugovallada.wallet.util.TypeEnum;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WalletItemService {

    WalletItem save(WalletItem wi);

    Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page);

    List<WalletItem> findByWalletAndType(Long wallet, TypeEnum type);

    BigDecimal sumByWalletId(Long wallet);
}