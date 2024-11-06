package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.UserAuthenticationDto;
import com.qlish.qlish_api.entity.User;

public class UserAuthenticationDtoMapper {

    public static UserAuthenticationDto mapUserToUserAuthDto(User user) {
        return UserAuthenticationDto.builder()
                .id(user.get_id())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .profileName(user.getProfileName())
                .role(user.getRole())
                .authProvider(user.getAuthProvider().toString())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
