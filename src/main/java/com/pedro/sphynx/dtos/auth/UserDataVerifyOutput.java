package com.pedro.sphynx.dtos.auth;

public record UserDataVerifyOutput(String token, Boolean isAdmin, Boolean result) {
}
