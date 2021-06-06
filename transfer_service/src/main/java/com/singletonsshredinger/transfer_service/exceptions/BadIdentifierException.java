package com.singletonsshredinger.transfer_service.exceptions;

public class BadIdentifierException extends RuntimeException {
    public BadIdentifierException(String message) {
        super(message);
    }

    public BadIdentifierException(String message, Throwable cause) {
        super(message, cause);
    }
}
