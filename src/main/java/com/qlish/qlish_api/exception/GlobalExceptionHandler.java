package com.qlish.qlish_api.exception;

import com.qlish.qlish_api.constants.AppConstants;
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
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.login.AccountException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler implements ErrorController {


    private final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(this));

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> handleAccountDisabledException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST, AppConstants.ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> handleBadCredentialsException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST, AppConstants.INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> handleAccessDeniedException(){
        return createHttpResponse(HttpStatus.UNAUTHORIZED, AppConstants.NOT_ENOUGH_PERMISSIONS);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> handleAccountLockedException(){
        return createHttpResponse(HttpStatus.UNAUTHORIZED, AppConstants.ACCOUNT_LOCKED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<HttpResponse> handleTokenExpiredException(ExpiredJwtException exception){
        return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<HttpResponse> handleEntityExistsException(EntityAlreadyExistException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ResponseEntity<HttpResponse> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpResponse> handleIllegalArgumentException(IllegalArgumentException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(AppConstants.METHOD_IS_NOT_ALLOWED, supportedMethod));
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpResponse> handleNoHandlerFoundException(){

        return createHttpResponse(HttpStatus.NOT_FOUND, AppConstants.PAGE_NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HttpResponse> handleEntityNotFoundException(EntityNotFoundException exception){
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<HttpResponse> handleAccountException(AccountException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @RequestMapping(AppConstants.ERROR_PATH)
    public ResponseEntity<HttpResponse> handleNotFound404(){
        return createHttpResponse(HttpStatus.NOT_FOUND, AppConstants.NO_MAPPING_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpResponse> handleIllegalStateException(IllegalStateException exception){
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<HttpResponse> handleInternalServerErrorException(InternalServerError exception){
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(QuestionsRetrievalException.class)
    public ResponseEntity<HttpResponse> handleQuestionsRetrievalException(QuestionsRetrievalException exception){
        LOGGER.error(exception.getMessage());
        return createHttpResponse( HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.QUESTIONS_RETRIEVAL_ERROR);
    }

    @ExceptionHandler(CustomDatabaseException.class)
    public ResponseEntity<HttpResponse> handleCustomDatabaseException(CustomDatabaseException exception){
        LOGGER.error(exception.getMessage());
        return createHttpResponse( HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.DATABASE_ERROR);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<HttpResponse> handleUnsupportedOperationException(UnsupportedOperationException exception){
        LOGGER.error(exception.getMessage());
        return createHttpResponse( HttpStatus.BAD_REQUEST, AppConstants.UNSUPPORTED_OPERATION);
    }

    @ExceptionHandler(TestSubmissionException.class)
    public ResponseEntity<HttpResponse> handleTestSubmissionException(TestSubmissionException exception){
        LOGGER.error(exception.getMessage());
        return createHttpResponse( HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.TEST_SUBMISSION_ERROR);
    }


    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String message){
        HttpResponse httpResponse = new HttpResponse(status.value(), status,
                status.getReasonPhrase(), message);
        return new ResponseEntity<>(httpResponse, status);
    }

}
