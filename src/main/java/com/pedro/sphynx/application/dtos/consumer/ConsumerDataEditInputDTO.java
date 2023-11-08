package com.pedro.sphynx.application.dtos.consumer;

import jakarta.validation.constraints.NotBlank;

public record ConsumerDataEditInputDTO (@NotBlank String tag){
}
