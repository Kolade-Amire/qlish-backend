package com.qlish.qlish_api.entity;

import com.qlish.qlish_api.constants.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("token")
public class TokenEntity {

    @Id
    private Integer id;
    @Indexed// Creates an index on this field for faster lookups
    private String userId;
    @Indexed
    private String token;
    @Builder.Default
    private String tokenType = TokenType.BEARER.name();
    private boolean isExpired;
    private boolean isRevoked;

    @Override
    public String toString(){
        return "User{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", type='" + tokenType + '\'' +
                ", isExpired='" + isExpired + '\'' +
                ", isRevoked='" + isRevoked + '\'' +
                '}';
    }


}
