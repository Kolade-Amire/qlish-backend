package com.qlish.qlish_api.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {

    UserEntity getUserByEmail(String email) throws UsernameNotFoundException;

    UserEntity saveUser(UserEntity user);
}
