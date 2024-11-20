package com.pedro.sphynx.application.dtos.access;

import jakarta.validation.constraints.NotBlank;

public record AccessDataFingerprintInput(
        @NotBlank byte[] fingerprint,
        @NotBlank String mac
) {
}