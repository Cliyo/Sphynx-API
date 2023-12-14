package com.pedro.sphynx.application.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDataLoginInput(
        @NotBlank @Email String user,
        @NotBlank String password
) {
}
