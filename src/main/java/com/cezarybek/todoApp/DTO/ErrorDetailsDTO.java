package com.cezarybek.todoApp.DTO;

import java.util.Date;

public class ErrorDetailsDTO {
    private final Date timestamp;
    private final String message;
    private final String details;

    public ErrorDetailsDTO(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
