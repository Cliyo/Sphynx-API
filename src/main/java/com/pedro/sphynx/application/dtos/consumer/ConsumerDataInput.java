package com.pedro.sphynx.application.dtos.consumer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsumerDataInput(
        @NotBlank String name,
        @NotBlank String ra,
        @NotBlank String tag,
        @NotNull Integer group){
}
