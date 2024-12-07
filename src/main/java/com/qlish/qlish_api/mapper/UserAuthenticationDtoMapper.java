package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.UserAuthenticationDto;
import com.qlish.qlish_api.model.User;

public class UserAuthenticationDtoMapper {

    public static UserAuthenticationDto mapUserToUserAuthDto(User user) {
        return UserAuthenticationDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .profileName(user.getProfileName())
                .profilePicture(user.getProfilePictureUrl())
                .role(user.getRole())
                .authProvider(user.getAuthProvider().toString())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
