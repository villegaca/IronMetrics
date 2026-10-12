package com.villegaca.ironmetrics.dto;

import java.time.LocalDateTime;

public record WorkoutRequest (
    String name,
    LocalDateTime startedAt,
    LocalDateTime completedAt
) {}
