package com.qlish.qlish_api.model;

import com.qlish.qlish_api.enums.auth.AuthProvider;
import com.qlish.qlish_api.enums.auth.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    private String profilePictureUrl;
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
    @Indexed
    private long allTimePoints;

    @Override
    public String toString(){
        return "User{" +
                "_id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", profilePicture='" + profilePictureUrl + '\'' +
                ", email='" + email + '\'' +
                ", username='" + profileName + '\'' +
                ", role='" + role.toString() + '\'' +
                ", authProvider='" + authProvider + '\'' +
                ", isEmailVerified='" + isEmailVerified + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastLoginAt='" + lastLoginAt + '\'' +
                ", totalPoints='" + allTimePoints + '\'' +
                '}';
    }

}
