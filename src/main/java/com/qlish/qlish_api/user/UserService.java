package com.qlish.qlish_api.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {

    UserEntity getUserByEmail(String email) throws UsernameNotFoundException;

    UserEntity saveUser(UserEntity user);

    boolean userExists(String email);
}
