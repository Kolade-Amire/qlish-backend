package com.qlish.qlish_api.security.authenticaton.oauth2;

public interface OAuth2UserInfo {

    String getId();
    String getName();
    String getEmail();
    String getImageUrl();
}
