package com.pedro.sphynx.application.dtos.local;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocalDataInput(
        @NotBlank String name,
        @NotBlank String mac,
        @NotBlank String group) {
}
