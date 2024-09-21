package com.qlish.qlish_api.constants;

public class AppConstants {
    public static final String API_VERSION = "v1";
    public static final String BASE_URL = "/api/" + API_VERSION;
    public static final String LOGOUT_URL = BASE_URL + "/auth/logout";
    public static final String USER_NOT_FOUND = "User does not exist.";
    public static final String QUESTIONS_RETRIEVAL_ERROR = "Couldn't retrieve questions. Please check request.";
    public static final String NO_MAPPING_FOUND = "No mapping found for this url";
    public static final String DATABASE_ERROR = "Unexpected Database Error.";

    public static final String UNSUPPORTED_OPERATION = "Unsupported Operation.";

    public static final String TEST_SUBMISSION_ERROR = "An unexpected error occurred while submitting test";
    public static final String PAGE_NOT_FOUND = "This page was not found";

    public static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    public static final String INCORRECT_CREDENTIALS = "Username/password incorrect, please try again.";
    public static  final String UNSUPPORTED_TEST_TYPE = "Test type is not supported.";
    public static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration.";
    public static final String NOT_ENOUGH_PERMISSIONS = "You do not have sufficient permissions to access this endpoint.";
    public static final String ERROR_PATH = "/error";

}
