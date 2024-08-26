package com.qlish.qlish_api.util;

public class SecurityConstants {
    public static final Long REFRESH_TOKEN_EXPIRATION = 432000000L; // 5 days
    public static final Long ACCESS_TOKEN_EXPIRATION = 900000L; // 15 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String JWT_ISSUER = "qlish";
    public static final String AUTHORITIES = "authorities";
    public static final String AUDIENCE = "qlish-backend";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED = "You do not have permission to access this page";
    public static final String AUTHENTICATED_MESSAGE = "User authenticated successfully!";
    public static final String REGISTERED_MESSAGE = "User registered successfully!";

    public static final String[] PUBLIC_URLS = {
            AppConstants.BASE_URL + "/auth/**"
    };
    public static String JWT_SECRET_KEY = System.getenv("JWT_SECRET_KEY");
}
