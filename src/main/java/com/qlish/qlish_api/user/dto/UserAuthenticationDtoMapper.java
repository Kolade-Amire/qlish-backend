package com.qlish.qlish_api.user.dto;

import com.qlish.qlish_api.user.UserEntity;

public class UserAuthenticationDtoMapper {

    public static UserAuthenticationDto mapUserToUserAuthDto(UserEntity user) {
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
