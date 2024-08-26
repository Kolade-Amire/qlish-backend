package com.qlish.qlish_api.user.Token;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenEntity {

    private Integer id;
    private String token;
    private String tokenType;
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
