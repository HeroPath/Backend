package com.gianca1994.aowebbackend.exception;

/**
 * @Author: Gianca1994
 * Explanation: NotFoundException
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
