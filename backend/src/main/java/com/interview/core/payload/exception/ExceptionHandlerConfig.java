package com.interview.core.payload.exception;

import com.interview.core.payload.ErrorResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.OptimisticLockException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerConfig {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponsePayload handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorResponsePayload(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponsePayload handleOptimisticLockException(OptimisticLockException exception) {
        log.error("Optimistic Lock", exception);
        return new ErrorResponsePayload("Your operation did not succeed due to a conflicting operation from another user; please try again");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponsePayload handleGenericError(Throwable error) {
        log.error("Unexpected error", error);
        return new ErrorResponsePayload("This is not good; we are unable to perform your operation");
    }
}
