package com.qlish.qlish_api.model;

import com.qlish.qlish_api.enums.TokenType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@RedisHash("token")
public class TokenEntity {

    @Id
    private Integer id;
    @Indexed
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
