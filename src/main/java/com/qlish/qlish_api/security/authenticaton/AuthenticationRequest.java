package com.qlish.qlish_api.security.authenticaton;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
}
