package com.pedro.sphynx.application.dtos.consumer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ConsumerDataInput(
        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp="\\d{13}")
        String ra,

        @NotBlank
        @Pattern(regexp="([A-Z]{2} \\d{1,2} ?)+")
        String tag,

        @NotNull
        Integer group){
}
