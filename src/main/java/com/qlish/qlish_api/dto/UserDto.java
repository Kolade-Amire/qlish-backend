package com.qlish.qlish_api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private String id;
    private String email;
    private String fullName;
    private String profilePictureUrl;
    private String profileName;
    private long allTimePoints;

}
