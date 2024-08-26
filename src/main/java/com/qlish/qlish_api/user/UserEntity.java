package com.qlish.qlish_api.user;

import com.qlish.qlish_api.test.TestEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "users")
public class UserEntity {

    @Id
    private ObjectId _id;
    @Indexed(unique = true)
    private String email;
    private String name;
    @Field("username")
    @Indexed(unique = true)
    private String profileName;
    private String password;
    private Role role;
    private String authProvider;
    private LocalDate passwordLastChangedDate;
    private boolean isBlocked;
    private boolean isAccountExpired;
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
                ", role='" + role.toString() + '\'' +
                ", authProvider='" + authProvider + '\'' +
                ", isEmailVerified='" + isEmailVerified + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastLoginAt='" + lastLoginAt + '\'' +
                '}';
    }

}
