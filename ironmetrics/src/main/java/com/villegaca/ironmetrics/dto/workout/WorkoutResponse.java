package com.villegaca.ironmetrics.dto.workout;

import java.time.LocalDateTime;

public record WorkoutResponse (
    Long id,
    String name,
    LocalDateTime startedAt,
    LocalDateTime completedAt
 ) {}
