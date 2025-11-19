package com.eclubmaven.api_secure.modules.auth.dto;

public record AuthUserDto(
        String id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String pseudo
) {}