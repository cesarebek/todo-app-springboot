package com.cezarybek.todoApp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthResponseDTO {
    private final String accessToken;
    private final Date issueDate;
    private final Date expirationDate;
    private final String tokenType;

    public AuthResponseDTO(String accessToken, Date issueDate, Date expirationDate, String tokenType) {
        this.accessToken = accessToken;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.tokenType = tokenType;
    }
}
