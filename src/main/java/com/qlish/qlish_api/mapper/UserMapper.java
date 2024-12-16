package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.UserDto;
import com.qlish.qlish_api.model.User;

import java.util.List;

public class UserMapper {

    public static UserDto mapUserToDto(User user){
        return UserDto.builder()
                .id(user.getId().toHexString())
                .fullName(user.getFirstname() + " " + user.getLastname())
                .email(user.getEmail())
                .profileName(user.getProfileName())
                .allTimePoints(user.getAllTimePoints())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }

    public static List<UserDto> mapUserListToDto(List<User> users){
        return users.stream().map(
                UserMapper::mapUserToDto
        ).toList();
    }
}
