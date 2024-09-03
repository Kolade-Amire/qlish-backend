package com.qlish.qlish_api.security.authenticaton;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qlish.qlish_api.user.dto.UserAuthenticationDto;
import com.qlish.qlish_api.util.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuthenticationResponse {

    private HttpResponse httpResponse;
    @JsonProperty("access_token")
    private String accessToken;
    private UserAuthenticationDto user;
}
