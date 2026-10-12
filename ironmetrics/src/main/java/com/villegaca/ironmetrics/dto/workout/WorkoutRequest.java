package com.villegaca.ironmetrics.dto.workout;

import java.time.LocalDateTime;

public record WorkoutRequest (
    String name,
    LocalDateTime startedAt,
    LocalDateTime completedAt
) {}
