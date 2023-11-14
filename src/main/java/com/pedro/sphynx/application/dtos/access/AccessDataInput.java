package com.pedro.sphynx.application.dtos.access;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AccessDataInput(
        @NotBlank String tag,
        @NotBlank String local
) {
}
