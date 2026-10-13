package com.villegaca.ironmetrics.dto.muscle;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MuscleVolumeResponse (
    Long muscleId,
    LocalDate date,
    BigDecimal totalVolume
){}
