package com.vsachkovsky.countiesandcities.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerController {

    private static final String NOT_FOUND = "The requested object not found";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleEntityNotFoundException(final NotFoundException e) {
        log.error(e.getMessage());
        return buildErrorDto(HttpStatus.NOT_FOUND, e, NOT_FOUND);
    }

    private ErrorDto buildErrorDto(HttpStatus status, Exception e, String reason) {
        return ErrorDto.errorDtoBuilder()
                .code(status.value())
                .status(status)
                .reason(reason)
                .errorMessage(e.getLocalizedMessage())
                .timestamp(ZonedDateTime.now())
                .build();
    }
}
