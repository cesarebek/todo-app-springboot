package com.cezarybek.todoApp.exception;

import com.cezarybek.todoApp.DTO.ErrorDetailsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Handle specific exceptions
    @ExceptionHandler(TodoAppException.class)
    public ResponseEntity<ErrorDetailsDTO> handleTodoAppException(TodoAppException exception,
                                                                  WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, exception.getStatus());
    }

    //Global exceptions
}
