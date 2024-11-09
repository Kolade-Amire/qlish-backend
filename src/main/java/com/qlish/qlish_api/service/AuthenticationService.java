package com.qlish.qlish_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.constants.SecurityConstants;
import com.qlish.qlish_api.entity.TokenEntity;
import com.qlish.qlish_api.entity.User;
import com.qlish.qlish_api.entity.UserPrincipal;
import com.qlish.qlish_api.enums.auth.AuthProvider;
import com.qlish.qlish_api.enums.auth.Role;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.exception.EntityAlreadyExistException;
import com.qlish.qlish_api.exception.PasswordsDoNotMatchException;
import com.qlish.qlish_api.mapper.UserAuthenticationDtoMapper;
import com.qlish.qlish_api.security.data.AuthenticationRequest;
import com.qlish.qlish_api.security.data.AuthenticationResponse;
import com.qlish.qlish_api.security.data.RegistrationRequest;
import com.qlish.qlish_api.security.data.RegistrationResponse;
import com.qlish.qlish_api.util.HttpResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    //TODO: implement a regex checker for a strong password
    public String doPasswordsMatch(String p1, String p2) {
        if (!p1.equals(p2)) {
            throw new PasswordsDoNotMatchException("Passwords do not match!");
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
        try {
            validateNewUser(request.getEmail());
            var password = doPasswordsMatch(request.getPassword(), request.getConfirmPassword());

            var user = User.builder()
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
        } catch (EntityAlreadyExistException e) {
            throw new EntityAlreadyExistException(e.getMessage());
        } catch (PasswordsDoNotMatchException e){
            throw new PasswordsDoNotMatchException(e.getMessage());
        } catch(Exception e){
            throw new CustomQlishException("Error registering new user: ", e);
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userService.getUserByEmail(request.getEmail());

            user.setLastLoginAt(LocalDateTime.now());


            try {
                tokenService.deleteTokenByUserId(user.get_id().toString());
            } catch (Exception e) {
                LOGGER.info("Token Expired. Failed to delete user's existing token.");
            }


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
        } catch (AuthenticationException e) {
            throw new CustomQlishException("Error Authenticating user: ", e);
        } catch (Exception e){
            throw new CustomQlishException("User Authentication failed: ", e);
        }


    }

    private void saveUserRefreshToken(User user, String token) {

        var newTokenEntity = TokenEntity.builder()
                .userId(user.get_id().toHexString())
                .token(token)
                .tokenType(OAuth2AccessToken.TokenType.BEARER.getValue())
                .isExpired(false)
                .isRevoked(false)
                .build();

        try {
            tokenService.saveToken(newTokenEntity);
        } catch (Exception e) {
            throw new CustomQlishException(e.getMessage(), e);
        }
    }


    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String accessToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return;
        }

        try {
            accessToken = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
            userEmail = jwtService.extractUsername(accessToken);

            if (userEmail != null) {
                var user = this.userService.getUserByEmail(userEmail);
                var userPrincipal = new UserPrincipal(user);
                var refreshToken = tokenService.findTokenByUserId(user.get_id().toHexString());

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
        } catch (JwtException e) {
            throw new CustomQlishException("JWT could not be validated: ", e);
        } catch (UsernameNotFoundException e) {
            throw new CustomQlishException("User not found: ", e);
        } catch (Exception e) {
            throw new CustomQlishException("Error refreshing access token: ", e);
        }

    }


}

