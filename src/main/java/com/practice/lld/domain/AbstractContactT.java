package com.practice.lld.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@Data
public abstract class AbstractContactT {

    private UUID id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
