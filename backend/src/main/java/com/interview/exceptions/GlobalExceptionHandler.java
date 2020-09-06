package com.interview.exceptions;

import com.interview.exceptions.exceptions.CustomException;
import lombok.extern.java.Log;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;


@Log
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> list = ex.getBindingResult().
                getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"Invalid Json",ex);
        apiError.setErrors(list);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

//    @ExceptionHandler(value
//            = {UsernameNotFoundException.class})
//    protected ResponseEntity<?> notFound(
//            RuntimeException ex, WebRequest request) {
//        log.log(Level.SEVERE, ex.getMessage(), ex);
//        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "User Name Not Found", ex));
//    }

    @ExceptionHandler(value
            = {CustomException.class})
    protected ResponseEntity<?> customExceptionHandler(
            CustomException ex, WebRequest request) {

        log.log(Level.SEVERE, ex.getMessage(), ex);
        ApiError apiError = new ApiError(ex.getHttpStatus());
        apiError.setMessage(ex.getMessage());

        if(ex.getCause() != null){
            apiError.setDebugMessage(ex.getCause().getLocalizedMessage());
        }
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value
            = {DataIntegrityViolationException.class})
    protected ResponseEntity<?> dataIntegrationValidationHandler(
            DataIntegrityViolationException ex, WebRequest request) {

        log.log(Level.SEVERE, ex.getMessage(), ex);
        return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, ex.getLocalizedMessage(), ex));
    }

    @ExceptionHandler(value
            = {ConstraintViolationException.class})
    protected ResponseEntity<?> constraintViolationExceptionnHandler(
            ConstraintViolationException ex, WebRequest request) {
        log.log(Level.SEVERE, ex.getMessage(), ex);

        List<String> list = ex.getConstraintViolations().
                stream().map(i -> i.getPropertyPath() +" : "+ i.getMessage()).collect(Collectors.toList());

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request",ex);
        apiError.setErrors(list);
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(value
            = {Exception.class})
    protected ResponseEntity<?> handleUnknownExceptions(
            Exception ex, WebRequest request) {

        log.log(Level.SEVERE, ex.getMessage(), ex);

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(ex.getLocalizedMessage());
        apiError.setDebugMessage(ex.toString());

        return buildResponseEntity(apiError);
    }

}
