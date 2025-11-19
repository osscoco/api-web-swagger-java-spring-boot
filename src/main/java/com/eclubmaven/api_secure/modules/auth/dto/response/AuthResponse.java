package com.eclubmaven.api_secure.modules.auth.dto.response;

import com.eclubmaven.api_secure.modules.auth.dto.AuthUserDto;

public record AuthResponse(
        String token,
        AuthUserDto user
) {}