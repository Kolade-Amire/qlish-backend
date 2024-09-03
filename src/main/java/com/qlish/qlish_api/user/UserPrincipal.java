package com.qlish.qlish_api.user;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {

    private final UserEntity user;
    private Map<String, Object> attributes;



    public ObjectId getUserId() {
        return this.user.get_id();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().getAuthorities();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isAccountExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEmailVerified();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    //email is username
    @Override
    public String getUsername() {
        return user.getEmail();
    }


    public static UserPrincipal create(UserEntity user, Map<String, Object> attributes) {

        UserPrincipal userPrincipal = new UserPrincipal(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;

    }

    private void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return this.user.getFirstname() + " " + this.user.getLastname();
    }
}
