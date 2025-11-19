package com.eclubmaven.api_secure.modules.auth.dto.request;

public record LoginRequest(
        String email,
        String password
) {}