package com.eclubmaven.api_secure.modules.auth.mapper;

import com.eclubmaven.api_secure.models.UserEntity;
import com.eclubmaven.api_secure.modules.auth.dto.AuthUserDto;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthUserDto toMeDto(UserEntity user) {
        return new AuthUserDto(
                user.getId().toString(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getPseudo()
        );
    }
}
