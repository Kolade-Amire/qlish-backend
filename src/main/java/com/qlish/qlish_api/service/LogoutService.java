package com.qlish.qlish_api.service;

import com.qlish.qlish_api.constants.SecurityConstants;
import com.qlish.qlish_api.model.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenService tokenService;

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String token;
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return;
        }
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//        token = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
        tokenService.deleteTokenByUserId(principal.getUserId().toString());
        SecurityContextHolder.clearContext();

    }
}
