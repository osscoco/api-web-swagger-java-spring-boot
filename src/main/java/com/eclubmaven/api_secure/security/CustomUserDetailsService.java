package com.eclubmaven.api_secure.security;

import com.eclubmaven.api_secure.models.UserEntity;
import com.eclubmaven.api_secure.modules.auth.repository.AuthRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository users;

    public CustomUserDetailsService(AuthRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserEntity user = users.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
