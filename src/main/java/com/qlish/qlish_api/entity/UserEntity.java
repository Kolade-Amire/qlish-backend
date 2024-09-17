package com.qlish.qlish_api.entity;

import com.qlish.qlish_api.enums.auth_enums.AuthProvider;
import com.qlish.qlish_api.enums.auth_enums.Role;
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
    @Indexed(unique = true)
    private ObjectId _id;
    @Indexed(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    @Field("username")
    @Indexed(unique = true)
    private String profileName;
    private String password;
    private Role role;
    private AuthProvider authProvider;
    private LocalDate passwordLastChangedDate;
    private boolean isBlocked;
    private boolean isAccountExpired;
    private boolean isEmailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    @DocumentReference
    private Set<TestDetails> tests;

    @Override
    public String toString(){
        return "User{" +
                "_id=" + _id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
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
