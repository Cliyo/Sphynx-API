package com.pedro.sphynx.dtos.group;

import java.util.Set;

import com.pedro.sphynx.entities.WeekDay;

public record GroupDataEdit(
  String name,
  Set<WeekDay> weekDays
){}
