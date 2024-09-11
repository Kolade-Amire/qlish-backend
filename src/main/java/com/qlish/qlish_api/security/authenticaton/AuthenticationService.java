package com.qlish.qlish_api.security.authenticaton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.exception.EntityAlreadyExistException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.security.token.TokenEntity;
import com.qlish.qlish_api.security.token.TokenService;
import com.qlish.qlish_api.constants.auth_enums.AuthProvider;
import com.qlish.qlish_api.entity.UserEntity;
import com.qlish.qlish_api.entity.UserPrincipal;
import com.qlish.qlish_api.service.UserService;
import com.qlish.qlish_api.mapper.UserAuthenticationDtoMapper;
import com.qlish.qlish_api.constants.auth_enums.Role;
import com.qlish.qlish_api.util.HttpResponse;
import com.qlish.qlish_api.constants.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    //TODO: implement a regex checker for a strong password
    public String doPasswordsMatch(String p1, String p2) {
        if (!p1.equals(p2)) {
            throw new RuntimeException("Passwords do not match!");
        } else return p2;
    }

    private void validateNewUser(String email) {
        if (userService.userExists(email)) {
            throw new EntityAlreadyExistException(
                    String.format("User with email %s already exists", email)
            );
        }
    }


    public RegistrationResponse register(RegistrationRequest request) {
        validateNewUser(request.getEmail());
        var password = doPasswordsMatch(request.getPassword(), request.getConfirmPassword());

        var user = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .authProvider(AuthProvider.LOCAL)
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .isAccountExpired(false)
                .isEmailVerified(true)
                .profileName(request.getProfileName())
                .passwordLastChangedDate(LocalDate.now())
                .build();

        var savedUser = userService.saveUser(user);
        var userDto = UserAuthenticationDtoMapper.mapUserToUserAuthDto(savedUser);



        var httpResponse = HttpResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .httpStatusCode(HttpStatus.CREATED.value())
                .reason(HttpStatus.CREATED.getReasonPhrase())
                .message(SecurityConstants.REGISTERED_MESSAGE)
                .build();

        return RegistrationResponse.builder()
                .httpResponse(httpResponse)
                .user(userDto)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userService.getUserByEmail(request.getEmail());

        user.setLastLoginAt(LocalDateTime.now());
        tokenService.deleteTokenByUserId(user.get_id().toString());



        var userPrincipal = new UserPrincipal(user);

        var accessToken = jwtService.generateAccessToken(userPrincipal);
        var refreshToken = jwtService.generateRefreshToken(userPrincipal);

        saveUserRefreshToken(user, refreshToken);

        var response = HttpResponse.builder()
                .httpStatusCode(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .reason(HttpStatus.OK.getReasonPhrase())
                .message(SecurityConstants.AUTHENTICATED_MESSAGE)
                .build();

        var userDto = UserAuthenticationDtoMapper.mapUserToUserAuthDto(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .httpResponse(response)
                .user(userDto)
                .build();


    }

    private void saveUserRefreshToken(UserEntity user, String token) {

        var newTokenEntity = TokenEntity.builder()
                .userId(user.get_id().toString())
                .token(token)
                .tokenType(OAuth2AccessToken.TokenType.BEARER.getValue())
                .isExpired(false)
                .isRevoked(false)
                .build();

        tokenService.saveToken(newTokenEntity);
    }


    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String accessToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return;
        }

        accessToken = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
        userEmail = jwtService.extractUsername(accessToken);

        if (userEmail != null) {
            var user = this.userService.getUserByEmail(userEmail);
            var userPrincipal = new UserPrincipal(user);
            var refreshToken = tokenService.findTokenByUserId(user.get_id().toString()).orElseThrow(() -> new EntityNotFoundException("Unknown user or token"));

            if (jwtService.isTokenValid(refreshToken.getToken(), userPrincipal)) {
                var newAccessToken = jwtService.generateAccessToken(userPrincipal);


                var customHttpResponse = HttpResponse.builder()
                        .httpStatusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .reason(HttpStatus.OK.getReasonPhrase())
                        .message(SecurityConstants.REFRESHED_MESSAGE)
                        .build();

                var authResponse = AuthenticationResponse.builder()
                        .httpResponse(customHttpResponse)
                        .accessToken(newAccessToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }


        }


    }


}

