package com.github.hugovallada.wallet.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserWalletDTO {

    private Long id;
    
    @NotNull(message = "O usuario nao deve ser nulo")
    private Long user;

    @NotNull(message = "O wallet nao pode ser nulo")
    private Long wallet;

}