package com.qlish.qlish_api.util;

public class SecurityConstants {
    public static final Long REFRESH_TOKEN_EXPIRATION = 432000000L; // 5 days in milliseconds
    public static final Long ACCESS_TOKEN_EXPIRATION = 3600000L; // 1 hour in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String JWT_ISSUER = "qlish";
    public static final String AUTHORITIES = "authorities";
    public static final String AUDIENCE = "qlish-api";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED = "You do not have permission to access this page";
    public static final String AUTHENTICATED_MESSAGE = "User authenticated successfully!";
    public static final String REGISTERED_MESSAGE = "User registered successfully!";
    public static final String REFRESHED_MESSAGE = "Access token refreshed successfully!";



    public static final String[] PUBLIC_URLS = {
            AppConstants.BASE_URL + "/auth/**",
            "/oauth2/**",
            AppConstants.BASE_URL + "/oauth2/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    public static String JWT_SECRET_KEY = System.getenv("JWT_SECRET_KEY");
}
