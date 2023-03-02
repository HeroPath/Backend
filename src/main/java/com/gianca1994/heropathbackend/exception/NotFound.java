package com.gianca1994.heropathbackend.exception;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to throw a not found exception.
 */

public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }

    public NotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
