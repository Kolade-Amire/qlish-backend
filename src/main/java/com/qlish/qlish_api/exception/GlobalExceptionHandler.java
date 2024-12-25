package com.qlish.qlish_api.exception;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.constants.SecurityConstants;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(this));

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ProblemDetail> handleAccountDisabledException(DisabledException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, AppConstants.ACCOUNT_DISABLED);
        problemDetail.setTitle("Account Disabled");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, AppConstants.INCORRECT_CREDENTIALS);
        problemDetail.setTitle("Bad Credentials");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, SecurityConstants.ACCESS_DENIED);
        problemDetail.setTitle("Access Denied");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ProblemDetail> handleAccountLockedException(LockedException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, AppConstants.ACCOUNT_LOCKED);
        problemDetail.setTitle("Account Locked");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ProblemDetail> handleTokenExpiredException(ExpiredJwtException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, AppConstants.EXPIRED_SESSION);
        problemDetail.setTitle("Expired Session");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ProblemDetail> handleEntityExistsException(EntityAlreadyExistException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getLocalizedMessage());
        problemDetail.setTitle("Entity Already Exists");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ResponseEntity<ProblemDetail> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, SecurityConstants.PASSWORDS_MISMATCH);
        problemDetail.setTitle("Passwords Do Not Match");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
        problemDetail.setTitle("BAD REQUEST");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        problemDetail.setTitle("Entity Not Found");

        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }


    @RequestMapping(AppConstants.ERROR_PATH)
    public ResponseEntity<ProblemDetail> handleNotFound404(HttpServletRequest request) {
        LOGGER.error("No mapping found.");
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, AppConstants.NO_MAPPING_FOUND);
        problemDetail.setTitle("404 Not Found");

        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> handleIllegalStateException(IllegalStateException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
        problemDetail.setTitle("Illegal State");

        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ProblemDetail> handleInternalServerErrorException(InternalServerError exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        problemDetail.setTitle("Internal Server Error");

        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(QuestionsRetrievalException.class)
    public ResponseEntity<ProblemDetail> handleQuestionsRetrievalException(QuestionsRetrievalException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.TEST_QUESTIONS_RETRIEVAL_ERROR);
        problemDetail.setTitle("Test Questions Retrieval Error");

        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(CustomQlishException.class)
    public ResponseEntity<ProblemDetail> handleGeneralException(CustomQlishException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.GENERAL_ERROR_MESSAGE + ": " + exception.getLocalizedMessage());
        problemDetail.setTitle("Application Exception");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ProblemDetail> handleUnsupportedOperationException(UnsupportedOperationException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, AppConstants.UNSUPPORTED_OPERATION + ":\n" + exception.getLocalizedMessage());
        problemDetail.setTitle("Unsupported Operation");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(TestSubmissionException.class)
    public ResponseEntity<ProblemDetail> handleTestSubmissionException(TestSubmissionException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.TEST_SUBMISSION_ERROR);
        problemDetail.setTitle("Test Submission Error");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(TestResultException.class)
    public ResponseEntity<ProblemDetail> handleTestResultException(TestResultException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.TEST_RESULT_ERROR);
        problemDetail.setTitle("Test Result Error");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(UserPointsUpdateException.class)
    public ResponseEntity<ProblemDetail> handleUserPointsUpdateException(UserPointsUpdateException exception, HttpServletRequest request){
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.UPDATE_USER_POINTS_ERROR);
        problemDetail.setTitle("User Points Update Error");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());

        problemDetail.setTitle("Invalid Request");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(GenerativeAIException.class)
    public ResponseEntity<ProblemDetail> handleGenerativeAIException(GenerativeAIException exception, HttpServletRequest request){
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage());

        problemDetail.setTitle("Question Generation Error");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(LeaderboardException.class)
    public ResponseEntity<ProblemDetail> handleLeaderboardException(LeaderboardException exception, HttpServletRequest request){
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage());

        problemDetail.setTitle("Leaderboard Error");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(HandlerException.class)
    public ResponseEntity<ProblemDetail> handleHandlerException(HandlerException exception, HttpServletRequest request){
        LOGGER.error(exception.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage());

        problemDetail.setTitle("Test Handler Error");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }




}
