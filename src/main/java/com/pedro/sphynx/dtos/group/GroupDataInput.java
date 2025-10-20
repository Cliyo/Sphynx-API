package com.pedro.sphynx.dtos.group;

import java.util.List;

import com.pedro.sphynx.utils.enums.WeekDaysEnum;

import jakarta.validation.constraints.NotBlank;

public record GroupDataInput(
        @NotBlank String name,
        List<WeekDaysEnum> weekDays
){}
