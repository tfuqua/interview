package com.interview.exception;

public class BusinessRuleViolationException extends RuntimeException {
    private String code;
    private Object[] messageParams;

    public BusinessRuleViolationException(String code, Object... messageParams) {
        super(code);
        this.code = code;
        this.messageParams = messageParams;
    }

    public <T> T generateExceptionMessage(MessageCreator<T> creator) {
        return creator.create(code, messageParams);
    }
    @FunctionalInterface
    public interface MessageCreator<T> {
        public T create(String code, Object[] messageParams);
    }
}


