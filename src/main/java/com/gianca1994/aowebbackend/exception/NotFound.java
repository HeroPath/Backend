package com.gianca1994.aowebbackend.exception;

/**
 * @Author: Gianca1994
 * Explanation: NotFoundException
 */
public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }

    public NotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
