package com.villegaca.ironmetrics.dto.set;

import java.math.BigDecimal;

public record SetResponse (
    Long id,
    BigDecimal weight,
    Integer repetition,
    Integer setNumber,
    Integer restSeconds,
    boolean isCompleted
) {}
