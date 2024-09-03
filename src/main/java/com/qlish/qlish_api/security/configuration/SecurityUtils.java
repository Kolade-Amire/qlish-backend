package com.qlish.qlish_api.security.configuration;

import com.qlish.qlish_api.security.token.TokenService;
import com.qlish.qlish_api.security.authenticaton.JwtService;
import com.qlish.qlish_api.user.oauth2.CustomAuthenticationFailureHandler;
import com.qlish.qlish_api.user.CustomUserDetailsService;
import com.qlish.qlish_api.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityUtils {

    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtService jwtService;
    private final TokenService tokenService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
