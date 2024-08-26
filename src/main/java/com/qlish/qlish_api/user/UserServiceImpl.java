package com.qlish.qlish_api.user;

import com.qlish.qlish_api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        return null;
    }
}
