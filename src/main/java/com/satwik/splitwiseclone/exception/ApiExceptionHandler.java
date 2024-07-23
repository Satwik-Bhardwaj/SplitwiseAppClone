package com.satwik.splitwiseclone.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleBadRequestException(RuntimeException ex) {
        log.info("Runtime exception occurred: ", ex);
        String response = String.valueOf(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
