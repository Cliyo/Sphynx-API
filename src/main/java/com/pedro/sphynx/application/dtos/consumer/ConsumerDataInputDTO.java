package com.pedro.sphynx.application.dtos.consumer;

import jakarta.validation.constraints.NotBlank;

public record ConsumerDataInputDTO (
        @NotBlank String ra,
        @NotBlank String tag){
}
