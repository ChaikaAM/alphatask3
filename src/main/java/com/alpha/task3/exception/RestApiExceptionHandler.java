package com.alpha.task3.exception;

import com.alpha.task3.model.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {RestApiException.class})
    protected ResponseEntity<Object> apiErrorHanlder(RestApiException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new ExceptionMessage(ex.getMessage()),
                new HttpHeaders(),
                ex.getHttpStatus(),
                request
        );
    }
}
