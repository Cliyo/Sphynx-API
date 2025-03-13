package com.pedro.sphynx.application.dtos.consumer;

import jakarta.validation.constraints.Pattern;

public record ConsumerDataEditInput(
        String name,

        @Pattern(regexp="\\d{13}")
        String ra,

        @Pattern(regexp="([A-Z]{2} \\d{1,2} ?)+")
        String tag,

        Integer group)
{}
