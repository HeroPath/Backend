package com.gianca1994.aowebbackend.exception;

/**
 * @Author: Gianca1994
 * Explanation: ConflictException
 */
public class ConflictException extends Exception {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
