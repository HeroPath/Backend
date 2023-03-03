package com.gianca1994.heropathbackend.exception;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to throw a conflict exception.
 */

public class Conflict extends Exception {
    public Conflict(String message) {
        super(message);
    }

    public Conflict(String message, Throwable cause) {
        super(message, cause);
    }
}
