package com.github.hugovallada.wallet.controller;

import com.github.hugovallada.wallet.dto.UserWalletDTO;
import com.github.hugovallada.wallet.entity.User;
import com.github.hugovallada.wallet.entity.UserWallet;
import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.response.Response;
import com.github.hugovallada.wallet.service.UserWalletService;
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
@RequestMapping("/user-wallet")
public class UserWalletController {

    private UserWalletService service;

    @Autowired
    public UserWalletController(UserWalletService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Response<UserWalletDTO>> create(
            @Valid @RequestBody UserWalletDTO dto,
            BindingResult result
    ) {
        Response<UserWalletDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        UserWallet savedUWallet = service.save(toModel(dto));
        response.setData(toDto(savedUWallet));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    private UserWallet toModel(UserWalletDTO dto) {
        UserWallet uWallet = new UserWallet();
        uWallet.setId(dto.getId());

        User u = new User();
        u.setId(dto.getUser());

        Wallet w = new Wallet();
        w.setId(dto.getWallet());

        uWallet.setUsers(u);
        uWallet.setWallet(w);

        return uWallet;
    }

    private UserWalletDTO toDto(UserWallet uWallet) {
        UserWalletDTO dto = new UserWalletDTO();
        dto.setId(uWallet.getId());
        dto.setUser(uWallet.getUsers().getId());
        dto.setWallet(uWallet.getWallet().getId());

        return dto;
    }
}