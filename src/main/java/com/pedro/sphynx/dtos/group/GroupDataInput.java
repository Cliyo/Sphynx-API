package com.pedro.sphynx.dtos.group;

import jakarta.validation.constraints.NotBlank;

public record GroupDataInput(
        @NotBlank String name){
}
