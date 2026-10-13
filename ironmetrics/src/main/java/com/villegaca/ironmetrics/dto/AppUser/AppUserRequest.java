package com.villegaca.ironmetrics.dto.AppUser;

import jakarta.validation.constraints.NotBlank;

public record AppUserRequest (
    @NotBlank 
    String firstName,

    @NotBlank 
    String lastName,

    @NotBlank 
    String email,

    @NotBlank 
    String password
) {}
