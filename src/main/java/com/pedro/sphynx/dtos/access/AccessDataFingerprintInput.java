package com.pedro.sphynx.dtos.access;

import jakarta.validation.constraints.NotBlank;

public record AccessDataFingerprintInput(
        @NotBlank String fingerprint,
        @NotBlank String mac
) {
}