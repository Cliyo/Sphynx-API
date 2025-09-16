package com.pedro.sphynx.dtos.consumer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ConsumerDataInput(
        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp="\\d{13}", message="RA inválido")
        String ra,

        @Pattern(regexp="^([A-Z0-9]{2,} ?)+$", message="Tag inválida")
        String tag,

        @NotNull
        Long userId,

        @NotNull
        Integer group,
        
        long fingerprint){
}
