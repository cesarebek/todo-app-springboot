package com.cezarybek.todoApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private List<UserDTO> users;
    private int pageNum;
    private int pageSize;
    private long totalRecords;
    private int totalPages;
    private boolean last;
}
