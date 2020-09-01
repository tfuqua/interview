package com.interview.service.exception;

import com.interview.common.exception.DomainException;

public class DuplicateBookNameException extends DomainException {
    public DuplicateBookNameException(String name) {
        super("book.name.existing", name);
    }
}
