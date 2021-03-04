package com.github.hugovallada.wallet.controller;

import com.github.hugovallada.wallet.dto.WalletItemDTO;
import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.entity.WalletItem;
import com.github.hugovallada.wallet.repository.WalletItemRepository;
import com.github.hugovallada.wallet.response.Response;
import com.github.hugovallada.wallet.service.WalletItemService;
import com.github.hugovallada.wallet.util.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/walletitem")
public class WalletItemController {

    @Autowired
    private WalletItemRepository repository;

    @Autowired
    private WalletItemService service;


    @PostMapping
    public ResponseEntity<Response<WalletItemDTO>> create(
            @Valid @RequestBody WalletItemDTO dto,
            BindingResult result
    ) {
        Response<WalletItemDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(response);
        }

        WalletItem wi = service.save(toModel(dto));
        response.setData(toDto(wi));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @GetMapping("/type/{wallet}")
    public ResponseEntity<Response<Page<WalletItemDTO>>> findBetweenDates(
            @PathVariable("wallet") Long wallet,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        Response<Page<WalletItemDTO>> response = new Response<>();
        Page<WalletItem> items = service.findBetweenDates(wallet, startDate, endDate, page);
        Page<WalletItemDTO> dtos = items.map(i -> toDto(i));
        response.setData(dtos);
        return ResponseEntity.ok().body(response);
    }

    private WalletItemDTO toDto(WalletItem wi) {
        WalletItemDTO dto = new WalletItemDTO();
        dto.setId(wi.getId());
        dto.setWallet(wi.getWallet().getId());
        dto.setDate(wi.getDate());
        dto.setType(wi.getType().getValue());
        dto.setDescription(wi.getDescription());
        dto.setValue(wi.getValue());

        return dto;
    }

    private WalletItem toModel(WalletItemDTO dto) {
        Wallet w = new Wallet();
        w.setId(dto.getWallet());

        WalletItem wi = new WalletItem();
        wi.setId(dto.getId());
        wi.setDate(dto.getDate());
        wi.setDescription(dto.getDescription());
        wi.setWallet(w);
        wi.setValue(dto.getValue());
        wi.setType(TypeEnum.valueOf(dto.getType()));

        return wi;
    }
}