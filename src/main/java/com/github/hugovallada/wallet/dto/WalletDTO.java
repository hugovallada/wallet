package com.github.hugovallada.wallet.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WalletDTO {

    private Long id;

    @Length(min = 3, message = "Name deve ter no minimo 3 caracteres")
    @NotNull(message = "Name nao pode ser nulo")
    private String name;

    @NotNull(message = "O value deve ser passado")
    private BigDecimal value;
}