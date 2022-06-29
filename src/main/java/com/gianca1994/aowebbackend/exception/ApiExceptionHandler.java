package com.gianca1994.aowebbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @Author: Gianca1994
 * Explanation: ApiExceptionHandler
 */

@ControllerAdvice
public class ApiExceptionHandler {

    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST; // 400
    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND; // 404
    private final HttpStatus CONFLICT = HttpStatus.CONFLICT; // 409

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(new ExceptionModel(
                e.getMessage(),
                BAD_REQUEST,
                BAD_REQUEST.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        ), BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ExceptionModel(
                e.getMessage(),
                NOT_FOUND,
                NOT_FOUND.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        ), NOT_FOUND);
    }

    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<Object> handleConflictException(ConflictException e) {
        return new ResponseEntity<>(new ExceptionModel(
                e.getMessage(),
                CONFLICT,
                CONFLICT.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        ), CONFLICT);
    }
}
