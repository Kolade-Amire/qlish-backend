package com.qlish.qlish_api.service;

import com.qlish.qlish_api.entity.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {

    UserEntity getUserByEmail(String email) throws UsernameNotFoundException;

    UserEntity saveUser(UserEntity user);

    boolean userExists(String email);
}
