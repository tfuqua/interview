package com.interview.exception;

import javax.persistence.EntityNotFoundException;

public class ResourceNotFoundException extends EntityNotFoundException {
    private final String code;
    private Long id;
    private String className;

    public ResourceNotFoundException(Class<?> cls, Long id) {
        super(cls.getSimpleName() + " { id: " + id + " } not found");
        this.id = id;
        this.className =cls.getSimpleName();
        this.code=cls.getSimpleName().toLowerCase()+"."+"not.found";
    }

    public <T> T generateExceptionMessage(MessageCreator<T> creator) {
        return creator.create(className, code, id);
    }

    @FunctionalInterface
    public interface MessageCreator<T> {
        public T create(String className, String code, Long id);
    }
}


