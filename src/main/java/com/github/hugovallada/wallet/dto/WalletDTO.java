package com.github.hugovallada.wallet.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WalletDTO {

    private Long id;

    @Length(min = 3)
    @NotNull(message = "Name nao pode ser nulo")
    private String name;

    @NotNull
    private BigDecimal value;
}