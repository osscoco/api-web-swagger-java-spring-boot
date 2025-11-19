package com.eclubmaven.api_secure.modules.auth.controller;

import com.eclubmaven.api_secure.modules.auth.dto.*;
import com.eclubmaven.api_secure.modules.auth.dto.request.LoginRequest;
import com.eclubmaven.api_secure.modules.auth.dto.request.RegisterRequest;
import com.eclubmaven.api_secure.modules.auth.dto.response.AuthResponse;
import com.eclubmaven.api_secure.modules.auth.service.IAuthService;
import com.eclubmaven.api_secure.security.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService service;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthController(IAuthService service, TokenBlacklistService tokenBlacklistService) {
        this.service = service;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthUserDto> me(Authentication auth) {
        return ResponseEntity.ok(service.getAuthMe(auth.getName()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.revoke(token);
        }
        return ResponseEntity.noContent().build();
    }
}