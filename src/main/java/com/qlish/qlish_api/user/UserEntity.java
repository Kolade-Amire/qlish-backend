package com.qlish.qlish_api.user;

import com.qlish.qlish_api.test.TestEntity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "users")
public class UserEntity {

    @Id
    private ObjectId _id;
    private String email;
    private String name;
    private String profileName;
    private String password;

    @DocumentReference
    private Set<String> tokens;
    private Set<Role> roles;
    private String authProvider;
    private boolean isEmailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    @DocumentReference
    private Set<TestEntity> tests;

    @Override
    public String toString(){
        return "User{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + profileName + '\'' +
                ", role='" + roles.toString() + '\'' +
                ", authProvider='" + authProvider + '\'' +
                ", isEmailVerified='" + isEmailVerified + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastLoginAt='" + lastLoginAt + '\'' +
                '}';
    }

}
