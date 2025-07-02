package com.javaweb.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String messages) {
        super(messages);
    }
}
