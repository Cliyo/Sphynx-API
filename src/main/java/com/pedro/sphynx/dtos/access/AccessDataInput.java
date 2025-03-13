package com.pedro.sphynx.dtos.access;

import jakarta.validation.constraints.NotBlank;

public record AccessDataInput(
        @NotBlank String tag,
        @NotBlank String mac
) {
}
