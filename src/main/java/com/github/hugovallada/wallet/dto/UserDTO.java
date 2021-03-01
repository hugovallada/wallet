package com.github.hugovallada.wallet.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    private Long id;

    @Email(message = "Email inválido")
    private String email;

    @Length(min = 3, max = 50, message = "Nome com tamanho inválido (3 - 50)")
    private String name;

    @NotNull
    @Length(min = 6, max = 50, message = "A senha deve ter entre 6 e 50 caracteres")
    private String password;

}