package com.gianca1994.heropathbackend.exception;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to throw a bad request exception.
 */

public class BadReq extends RuntimeException {
    public BadReq(String message) {
        super(message);
    }

    public BadReq(String message, Throwable cause) {
        super(message, cause);
    }
}
