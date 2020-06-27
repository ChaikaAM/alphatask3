package com.alpha.task3.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RestApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String message;

}
