package com.alex.excel.users.dto;

import com.alex.excel.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedUsersResponseDto {
    List<User> users;
    private int pageNumber;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isLastOne;
}
