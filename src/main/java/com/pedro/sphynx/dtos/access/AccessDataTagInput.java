package com.pedro.sphynx.dtos.access;

import jakarta.validation.constraints.NotBlank;

public record AccessDataTagInput(
        @NotBlank String tag,
        @NotBlank String mac
) {
}
