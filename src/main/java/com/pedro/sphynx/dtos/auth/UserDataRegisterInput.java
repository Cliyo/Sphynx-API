package com.pedro.sphynx.dtos.auth;

import java.util.List;

import com.pedro.sphynx.utils.enums.PermissionMenuEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserDataRegisterInput(
        @NotBlank 
        @Email 
        String 
        user,

        @NotBlank 
        String 
        name,

        @NotBlank
        @Pattern(regexp="\\d{13}", message="RA inválido")
        String ra,

        @NotNull
        Integer groupId,

        @NotNull
        List<PermissionMenuEnum> permissionMenu,

        @NotBlank
        String tag,

        @NotNull
        Boolean isAdmin
) {
}
