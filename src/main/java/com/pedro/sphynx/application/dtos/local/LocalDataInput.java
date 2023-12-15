package com.pedro.sphynx.application.dtos.local;

import jakarta.validation.constraints.NotBlank;

public record LocalDataInput(
        @NotBlank String name,
        @NotBlank String mac) {
}
