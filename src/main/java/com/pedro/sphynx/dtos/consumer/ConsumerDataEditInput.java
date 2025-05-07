package com.pedro.sphynx.dtos.consumer;

import jakarta.validation.constraints.Pattern;

public record ConsumerDataEditInput(
        String name,

        @Pattern(regexp="\\d{13}", message="RA inválido")
        String ra,

        @Pattern(regexp="^([A-Z0-9]{2,} ?)+$", message="Tag inválida")
        String tag,

        Integer group,
        
        long fingerprint)
{}
