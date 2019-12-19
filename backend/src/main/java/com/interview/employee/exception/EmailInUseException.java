package com.interview.employee.exception;

import com.interview.core.payload.exception.DomainException;

public class EmailInUseException extends DomainException {
    public EmailInUseException() {
        super("Email is already registered for an other employee");
    }
}
