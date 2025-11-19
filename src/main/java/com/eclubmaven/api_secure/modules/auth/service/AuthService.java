package com.eclubmaven.api_secure.modules.auth.service;

import com.eclubmaven.api_secure.models.*;
import com.eclubmaven.api_secure.modules.auth.dto.*;
import com.eclubmaven.api_secure.modules.auth.dto.request.LoginRequest;
import com.eclubmaven.api_secure.modules.auth.dto.request.RegisterRequest;
import com.eclubmaven.api_secure.modules.auth.dto.response.AuthResponse;
import com.eclubmaven.api_secure.modules.auth.mapper.AuthMapper;
import com.eclubmaven.api_secure.modules.auth.repository.AuthRepository;
import com.eclubmaven.api_secure.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final AuthRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final AuthMapper mapper;

    public AuthService(AuthRepository users,
                       PasswordEncoder encoder,
                       AuthenticationManager authManager,
                       JwtService jwt,
                       AuthMapper mapper) {
        this.users = users;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwt = jwt;
        this.mapper = mapper;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (users.existsByEmail(request.email())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setPseudo(request.pseudo());
        user.setPassword(encoder.encode(request.password()));

        UserEntity saved = users.save(user);

        return new AuthResponse(jwt.generateToken(saved.getEmail()), mapper.toMeDto(saved));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserEntity user = users.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return new AuthResponse(jwt.generateToken(user.getEmail()), mapper.toMeDto(user));
    }

    @Override
    public AuthUserDto getAuthMe(String email) {
        UserEntity user = users.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return mapper.toMeDto(user);
    }

    @Override
    public void logout() {}
}
