package com.eclubmaven.api_secure.modules.auth.dto.request;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email @NotBlank String email,
        String phone,
        @NotBlank String pseudo,
        @NotBlank String password
) {}