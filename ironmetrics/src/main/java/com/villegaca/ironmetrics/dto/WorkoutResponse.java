package com.villegaca.ironmetrics.dto;

import java.time.LocalDateTime;

public record WorkoutResponse (
    Long id,
    String name,
    LocalDateTime startedAt,
    LocalDateTime completedAt
 ) {}
