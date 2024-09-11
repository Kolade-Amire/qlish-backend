package com.qlish.qlish_api.listener;

import com.qlish.qlish_api.security.configuration.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final LoginAttemptService loginAttemptService;
    private final HttpServletRequest request;


    @Override
    public void onApplicationEvent(@NonNull AuthenticationSuccessEvent event) {
        String ipAddress = getClientIpAddress();
        loginAttemptService.loginSucceeded(ipAddress);
    }

    private String getClientIpAddress() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
