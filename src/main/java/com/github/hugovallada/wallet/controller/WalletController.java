package com.github.hugovallada.wallet.controller;

import com.github.hugovallada.wallet.dto.WalletDTO;
import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.response.Response;
import com.github.hugovallada.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private WalletService service;

    @Autowired
    public WalletController(WalletService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Response<WalletDTO>> create(@Valid @RequestBody WalletDTO dto, BindingResult result) {
        Response<WalletDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(res -> response.getErrors().add(res.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Wallet savedWallet = service.save(toModel(dto));
        response.setData(toDto(savedWallet));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    private Wallet toModel(WalletDTO dto) {
        Wallet wallet = new Wallet();
        wallet.setId(dto.getId());
        wallet.setName(dto.getName());
        wallet.setValue(dto.getValue());

        return wallet;
    }

    private WalletDTO toDto(Wallet wallet) {
        WalletDTO dto = new WalletDTO();
        dto.setId(wallet.getId());
        dto.setName(wallet.getName());
        dto.setValue(wallet.getValue());

        return dto;
    }
}