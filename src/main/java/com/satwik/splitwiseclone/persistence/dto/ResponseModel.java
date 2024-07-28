package com.satwik.splitwiseclone.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseModel<T>{

    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
    private List<ErrorDetails> errors;

}
