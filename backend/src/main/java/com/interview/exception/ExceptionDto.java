package com.interview.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDto {
    public ExceptionDto(RuntimeException exception) {
        message = exception.getMessage();
    }

    private String message;
}
