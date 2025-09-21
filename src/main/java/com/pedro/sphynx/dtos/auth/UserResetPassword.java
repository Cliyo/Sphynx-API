package com.pedro.sphynx.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record UserResetPassword (@NotBlank String newPassword) {

}
