package com.pedro.sphynx.dtos.group;

import java.util.Set;

import com.pedro.sphynx.utils.enums.WeekDaysEnum;

public record GroupDataEdit(
  String name,
  Set<WeekDaysEnum> weekDays
){}
