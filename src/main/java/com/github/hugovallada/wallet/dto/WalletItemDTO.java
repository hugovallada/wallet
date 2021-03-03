package com.github.hugovallada.wallet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class WalletItemDTO {

    private Long id;

    @NotNull(message = "Insira o id da carteira")
    private Long wallet;

    @NotNull(message = "Informe uma data")
    private Date date;

    @NotNull(message = "Insira o tipo")
    @Pattern(regexp = "^(ENTRADA|SAIDA)$", message = "O tipo aceita apenas ENTRADA ou SAIDA")
    private String type;

    @NotNull(message = "Insira uma descricao")
    @Length(min = 10, max = 200, message = "A descricao deve ter no minimo 5 caracteres")
    private String description;

    @NotNull(message = "Insira um valor")
    private BigDecimal value;
}