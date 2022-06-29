package com.gianca1994.aowebbackend.exception;

/**
 * @Author: Gianca1994
 * Explanation: BadRequestException
 */

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
