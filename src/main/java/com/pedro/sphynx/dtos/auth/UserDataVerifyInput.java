package com.pedro.sphynx.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record UserDataVerifyInput(
        @NotBlank String token) {
}
