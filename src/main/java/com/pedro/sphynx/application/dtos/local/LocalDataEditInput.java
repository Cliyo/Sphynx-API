package com.pedro.sphynx.application.dtos.local;

import jakarta.validation.constraints.NotBlank;

public record LocalDataEditInput(
        @NotBlank String mac) {
}
