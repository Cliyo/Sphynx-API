package com.pedro.sphynx.application.dtos.consumer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsumerDataEditInput(@NotBlank String tag, String permission){
}
