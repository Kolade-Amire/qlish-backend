package com.qlish.qlish_api.Token;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
    @Indexed // Creates an index on this field for faster lookups
    private String userId;
    private String token;
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
