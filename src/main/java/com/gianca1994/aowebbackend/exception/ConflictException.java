package com.gianca1994.aowebbackend.exception;

public class ConflictException extends Exception {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
