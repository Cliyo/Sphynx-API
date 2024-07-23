package com.pedro.sphynx.application.dtos.access;

import jakarta.validation.constraints.NotBlank;

public record AccessDataInput(
        @NotBlank String tag,
        @NotBlank String mac
) {
}
