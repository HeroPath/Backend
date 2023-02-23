package com.gianca1994.aowebbackend.exception;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to throw a bad request exception.
 */

public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }

    public BadRequest(String message, Throwable cause) {
        super(message, cause);
    }
}
