package com.qlish.qlish_api.service;

import com.qlish.qlish_api.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {

    User getUserByEmail(String email) throws UsernameNotFoundException;

    User saveUser(User user);

    boolean userExists(String email);
}
