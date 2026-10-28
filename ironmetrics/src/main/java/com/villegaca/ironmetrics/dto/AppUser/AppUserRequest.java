package com.villegaca.ironmetrics.dto.AppUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AppUserRequest (
    @NotBlank (message = "First Name is required")
    String firstName,

    @NotBlank (message = "Last Name is required")
    String lastName,

    @NotBlank (message = "email is required")
    @Email (message = "Enter a valid email")
    String email,

    @NotBlank (message = "Password is required")
    @Size (min = 6, message = "Password must be at least 6 characters long")
    @Pattern (
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9\\s])\\S+$",
        message = "Password must contain at least 1 uppercase character, one number, and one special character"
    )
    String password
) {}
