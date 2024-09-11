package com.qlish.qlish_api.util;

import com.qlish.qlish_api.constants.auth_enums.AuthProvider;
import com.qlish.qlish_api.entity.GoogleOAuth2UserInfo;
import com.qlish.qlish_api.entity.OAuth2UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationException("Sorry! Login with " + registrationId + " is not supported");
        }
    }
}
