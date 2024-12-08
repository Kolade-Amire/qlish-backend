package com.qlish.qlish_api.service;

import com.qlish.qlish_api.model.User;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {

    User getUserByEmail(String email) throws UsernameNotFoundException;

    User findUserById(ObjectId id);

    User saveUser(User user);

    void updateUserAllTimePoints(ObjectId id, int testPoints);

    boolean userExists(String email);
}
