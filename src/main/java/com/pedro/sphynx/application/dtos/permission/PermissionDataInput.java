package com.pedro.sphynx.application.dtos.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PermissionDataInput (
        @NotNull Integer level,
        @NotBlank String name){
}
