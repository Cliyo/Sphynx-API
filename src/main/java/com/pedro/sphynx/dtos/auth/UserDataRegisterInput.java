package com.pedro.sphynx.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDataRegisterInput(
        @NotBlank 
        @Email 
        String 
        user,

        @NotBlank 
        String 
        password,

        @NotBlank 
        String 
        name,

        @NotBlank
        @Pattern(regexp="\\d{13}", message="RA inválido")
        String ra,

        Boolean
        isAdmin
) {
}
