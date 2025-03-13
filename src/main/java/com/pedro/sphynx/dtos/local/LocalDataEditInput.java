package com.pedro.sphynx.dtos.local;

import jakarta.validation.constraints.NotBlank;

public record LocalDataEditInput(
        @NotBlank
        String name
)
{}
