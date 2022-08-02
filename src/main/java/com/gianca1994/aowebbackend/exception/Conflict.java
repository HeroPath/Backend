package com.gianca1994.aowebbackend.exception;

/**
 * @Author: Gianca1994
 * Explanation: ConflictException
 */
public class Conflict extends Exception {
    public Conflict(String message) {
        super(message);
    }

    public Conflict(String message, Throwable cause) {
        super(message, cause);
    }
}
