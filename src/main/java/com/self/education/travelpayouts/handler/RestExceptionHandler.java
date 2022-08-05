package com.self.education.travelpayouts.handler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.self.education.travelpayouts.api.ErrorResponse;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGlobalException(final Exception ex, final WebRequest request) {
        //@formatter:off
        return new ErrorResponse(
                INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        //@formatter:on
    }
}
