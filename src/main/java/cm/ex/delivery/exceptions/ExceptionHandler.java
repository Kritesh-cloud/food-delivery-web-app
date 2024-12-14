package cm.ex.delivery.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.http.ProblemDetail;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.NoSuchElementException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({BadCredentialsException.class,SignatureException.class,
            ExpiredJwtException.class,MethodArgumentNotValidException.class,AccessDeniedException.class,
            NoSuchElementException.class,DateTimeException.class,DuplicateKeyException.class,
            UsernameNotFoundException.class,IOException.class})
    public ProblemDetail handleSecurityException(Exception ex) {

        ProblemDetail errorDetail = null;

        System.out.println("exception from handler :: "+ex);

        if (ex instanceof BadCredentialsException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("reason", "Username or password dosen't match");
        }

        if (ex instanceof SignatureException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("reason", "Invalid token");
        }

        if (ex instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("reason", "Expired token");
        }

        if (ex instanceof AccessDeniedException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("reason", ex.getMessage());
        }

        if (ex instanceof DateTimeException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getMessage());
            errorDetail.setProperty("reason", "Incorrect date format");
        }

        if (ex instanceof NoSuchElementException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
            errorDetail.setProperty("reason", "No element found");
        }

        if (ex instanceof DuplicateKeyException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
            errorDetail.setProperty("reason", "Duplicate Element Exception");
        }

        if (ex instanceof UsernameNotFoundException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
            errorDetail.setProperty("reason", "Invalid email or token");
        }

        if (ex instanceof IOException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("reason", "Invalid Inputs");
        }

        return errorDetail;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), ex.getMessage());
        errorDetail.setProperty("reason", "Internal server error.");
        return errorDetail;
    }

}
