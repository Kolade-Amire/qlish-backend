package com.qlish.qlish_api.user;

import com.qlish.qlish_api.util.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;


    @Override //in this context a user's email is their username
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity user = userRepository.findByEmail(username)
               .orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

       return new UserDetails() {
           @Override
           public Collection<? extends GrantedAuthority> getAuthorities() {
               return user.getRole().getAuthorities();
           }

           @Override
           public String getPassword() {
               return user.getPassword();
           }

           @Override
           public String getUsername() {
               return user.getEmail();
           }

           @Override
           public boolean isAccountNonLocked() {
               return !(user.isBlocked());
           }

           @Override
           public boolean isCredentialsNonExpired() {
               return !(user.isAccountExpired());
           }

           @Override
           public boolean isEnabled() {
               return user.isEmailVerified();
           }
       };
    }
}
