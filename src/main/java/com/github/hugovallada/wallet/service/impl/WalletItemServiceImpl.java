package com.github.hugovallada.wallet.service.impl;

import com.github.hugovallada.wallet.entity.WalletItem;
import com.github.hugovallada.wallet.repository.WalletItemRepository;
import com.github.hugovallada.wallet.service.WalletItemService;
import com.github.hugovallada.wallet.util.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class WalletItemServiceImpl implements WalletItemService {


    private WalletItemRepository repository;

    @Value("${pagination.items_per_page}")
    private int itemsPerPage;

    @Autowired
    public WalletItemServiceImpl(WalletItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public WalletItem save(WalletItem wi) {
        return repository.save(wi);
    }


    @Override
    public Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page) {

        PageRequest pg = PageRequest.of(page, itemsPerPage);

        return repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet, start, end, pg);
    }

    @Override
    public List<WalletItem> findByWalletAndType(Long wallet, TypeEnum type) {
        return repository.findByWalletIdAndType(wallet, type);
    }

    @Override
    public BigDecimal sumByWalletId(Long wallet) {
        return repository.sumByWalletId(wallet);
    }
}