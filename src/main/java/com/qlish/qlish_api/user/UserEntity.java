package com.qlish.qlish_api.user;

import com.qlish.qlish_api.test.TestEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Document
public class UserEntity {

    private ObjectId _id;
    private String email;
    private String name;
    private String profileName;
    private String password;
    private Set<String> tokens;
    private Set<Role> roles;
    private String authProvider;
    private boolean isEmailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private Set<TestEntity> tests;

}
