package com.pedro.sphynx.dtos.unit;

import jakarta.validation.constraints.NotBlank;

public record UnitDataInput(
        @NotBlank String name){
}
