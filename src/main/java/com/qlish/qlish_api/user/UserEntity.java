package com.qlish.qlish_api.user;

import com.qlish.qlish_api.test.TestEntity;
import lombok.*;
import org.apache.el.parser.Token;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

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
    @Indexed(unique = true)
    private String email;
    private String name;
    @Field("username")
    @Indexed(unique = true)
    private String profileName;
    private String password;


    private Set<Token> tokens;
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
