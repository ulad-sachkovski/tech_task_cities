package com.vsachkovsky.tech_task_cities.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public record ErrorDto(int code, HttpStatus status, String reason,
                       @JsonInclude(JsonInclude.Include.NON_NULL) String errorMessage,
                       @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") ZonedDateTime timestamp,
                       @JsonInclude(JsonInclude.Include.NON_NULL) List<String> errors,
                       @JsonInclude(JsonInclude.Include.NON_NULL) Map<String, Object> info) {

    @Builder(builderMethodName = "errorDtoBuilder")
    public ErrorDto(int code, HttpStatus status, String reason, String errorMessage, ZonedDateTime timestamp,
                    List<String> errors, Map<String, Object> info) {
        this.code = code;
        this.status = status;
        this.reason = reason;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
        this.errors = errors;
        this.info = info;
    }
}