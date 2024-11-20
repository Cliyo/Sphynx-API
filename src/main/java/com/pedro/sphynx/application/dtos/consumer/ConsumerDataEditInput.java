package com.pedro.sphynx.application.dtos.consumer;

import jakarta.validation.constraints.NotBlank;

public record ConsumerDataEditInput(@NotBlank String tag, int permission, byte[] fingerprint) {
}
