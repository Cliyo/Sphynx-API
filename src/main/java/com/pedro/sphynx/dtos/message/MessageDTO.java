package com.pedro.sphynx.dtos.message;

public record MessageDTO(
        int status,
        String message,
        Object data)
{
}
