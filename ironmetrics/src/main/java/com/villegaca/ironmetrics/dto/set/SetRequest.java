package com.villegaca.ironmetrics.dto.set;

import java.math.BigDecimal;

public record SetRequest (
    BigDecimal weight,
    Integer repetition,
    Integer restSeconds,
    boolean isCompleted
) {}
