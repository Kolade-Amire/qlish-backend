package com.qlish.qlish_api.exception;

import com.qlish.qlish_api.util.HttpResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.login.AccountException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler implements ErrorController {

    private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    private static final String INCORRECT_CREDENTIALS = "Username/password incorrect, please try again.";
    private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration.";
    private static final String NOT_ENOUGH_PERMISSIONS = "You do not have sufficient permissions to access this endpoint.";
    private static final String ERROR_PATH = "/error";

    private final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(this));

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(){
        return createHttpResponse(HttpStatus.UNAUTHORIZED, NOT_ENOUGH_PERMISSIONS);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> accountLockedException(){
        return createHttpResponse(HttpStatus.UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(ExpiredJwtException exception){
        return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<HttpResponse> entityExistsException(EntityAlreadyExistException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ResponseEntity<HttpResponse> passwordsDoNotMatchException(PasswordsDoNotMatchException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpResponse> noHandlerFoundException(){
        return createHttpResponse(HttpStatus.NOT_FOUND, "This page was not found");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HttpResponse> entityNotFoundException(EntityNotFoundException exception){
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<HttpResponse> accountException(AccountException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404(){
        return createHttpResponse(HttpStatus.NOT_FOUND, "No mapping found for this url");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpResponse> illegalStateException(IllegalStateException exception){
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception){
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String message){
        HttpResponse httpResponse = new HttpResponse(status.value(), status,
                status.getReasonPhrase(), message);
        return new ResponseEntity<>(httpResponse, status);
    }

}
