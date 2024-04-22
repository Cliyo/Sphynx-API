package com.pedro.sphynx.application.dtos.consumer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsumerDataInput(
        @NotBlank String ra,
        @NotBlank String tag,
        @NotNull int permission){
}
