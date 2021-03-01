package com.github.hugovallada.wallet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;

    @Email(message = "Email Invalido")
    private String email;

    @Length(min = 3, max = 50, message = "Nome com tamanho invalido (3 - 50)")
    private String name;

    @NotNull
    @Length(min = 6, max = 50, message = "A senha deve ter entre 6 e 50 caracteres")
    private String password;

}