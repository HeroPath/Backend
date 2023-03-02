package com.gianca1994.heropathbackend.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to create a model for the exceptions.
 */

public class ExceptionModel {
    private final String message;
    private final HttpStatus httpStatus;
    private final int codeError;
    private final ZonedDateTime timestamp;

    public ExceptionModel(String message, HttpStatus httpStatus, int codeError, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.codeError = codeError;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getCodeError() {
        return codeError;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
