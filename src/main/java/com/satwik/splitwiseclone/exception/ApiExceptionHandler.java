package com.satwik.splitwiseclone.exception;

import com.satwik.splitwiseclone.constants.enums.ErrorCode;
import com.satwik.splitwiseclone.persistence.dto.ErrorDetails;
import com.satwik.splitwiseclone.persistence.dto.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseModel<String>> handleBadRequestException(RuntimeException ex) {
        log.info("Runtime exception occurred: ", ex);
        ResponseModel<String> errorResponse = new ResponseModel<>();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrors(List.of(ErrorDetails.builder()
                .errorCode(ErrorCode.UNKNOWN_ERROR.getCode())
                .errorDisc(ErrorCode.UNKNOWN_ERROR.getMessage()).build()));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
