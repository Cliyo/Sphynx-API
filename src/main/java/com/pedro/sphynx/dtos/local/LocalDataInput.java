package com.pedro.sphynx.dtos.local;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record LocalDataInput(
        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp="^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$", message="MAC inválido")
        String mac,

        @NotNull List<Integer> groups) {
}
