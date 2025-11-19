package com.eclubmaven.api_secure.modules.auth.service;

import com.eclubmaven.api_secure.modules.auth.dto.*;
import com.eclubmaven.api_secure.modules.auth.dto.request.LoginRequest;
import com.eclubmaven.api_secure.modules.auth.dto.request.RegisterRequest;
import com.eclubmaven.api_secure.modules.auth.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthUserDto getAuthMe(String username);
    void logout();
}