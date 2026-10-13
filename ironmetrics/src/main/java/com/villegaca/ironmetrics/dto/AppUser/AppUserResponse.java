package com.villegaca.ironmetrics.dto.AppUser;

public record AppUserResponse (
    Long userId,
    String firstName,
    String lastName,
    String email
) {}
