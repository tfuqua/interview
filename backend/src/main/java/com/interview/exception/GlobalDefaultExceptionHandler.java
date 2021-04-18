package com.interview.exception;

import com.interview.exception.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GlobalDefaultExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    private final MessageSource messageSource;

    public GlobalDefaultExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleOtherException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        ErrorDto dto = new ErrorDto("internal.server.error", "Contact the system administrator");
        return new ResponseEntity(dto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        ErrorDto dto = exception.generateExceptionMessage((classname, code, id) -> new ErrorDto(code, "Couldn't find "+ classname + " { id: " + id + " }"));
        return new ResponseEntity(dto,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleBusinessRuleException(BusinessRuleViolationException exception) {
        logger.warn(exception.getMessage(), exception);
        ErrorDto dto = exception.generateExceptionMessage((code, messageParams) -> new ErrorDto(code, messageSource.getMessage(code, messageParams, Locale.getDefault())));
        return new ResponseEntity(dto,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        logger.warn(exception.getMessage(), exception);
        String[] messages = exception.getBindingResult().getFieldErrors().stream().map(er -> er.getField() + " field " + er.getDefaultMessage()).toArray(String[]::new);
        ErrorDto dto = new ErrorDto("validation.failed", messages);
        return new ResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }
}
