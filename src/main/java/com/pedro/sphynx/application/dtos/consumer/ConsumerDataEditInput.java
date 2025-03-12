package com.pedro.sphynx.application.dtos.consumer;

public record ConsumerDataEditInput(
        String name,
        String ra,
        String tag,
        Integer group)
{}
