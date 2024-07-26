package com.pedro.sphynx.application.dtos.group;

import jakarta.validation.constraints.NotBlank;

public record GroupDataInput(
        @NotBlank String name){
}
