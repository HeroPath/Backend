package com.gianca1994.aowebbackend.exception;

public class ExpiredTokenException extends Exception {
    public ExpiredTokenException(String message) {
        super(message);
    }

    public ExpiredTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
