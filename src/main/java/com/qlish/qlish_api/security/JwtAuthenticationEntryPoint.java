package com.qlish.qlish_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.util.HttpResponse;
import com.qlish.qlish_api.constants.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    @Override
    public void commence (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        var httpResponse = HttpResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .httpStatusCode(HttpStatus.FORBIDDEN.value())
                .message(SecurityConstants.FORBIDDEN_MESSAGE)
                .reason(HttpStatus.FORBIDDEN.getReasonPhrase())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}
