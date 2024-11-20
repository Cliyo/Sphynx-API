package com.pedro.sphynx.application.dtos.access;

import jakarta.validation.constraints.NotBlank;

public record AccessDataTagInput(
        @NotBlank String tag,
        @NotBlank String mac
) {
}