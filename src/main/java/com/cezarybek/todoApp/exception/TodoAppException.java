package com.cezarybek.todoApp.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TodoAppException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

}
