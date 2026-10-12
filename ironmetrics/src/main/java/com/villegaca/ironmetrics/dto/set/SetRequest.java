package com.villegaca.ironmetrics.dto.set;

import java.math.BigDecimal;

public record SetRequest (
    BigDecimal weight,
    Integer repetition,
    Integer setNumber,
    Integer restSeconds,
    boolean isCompleted
) {}
