package com.pedro.sphynx.application.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record UserDataVerifyInput(
        @NotBlank String token) {
}
