package com.alex.excel.users.service;

import com.alex.excel.users.dto.PaginatedUsersResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {

    void massiveUserCreationFromFile(MultipartFile file);

    PaginatedUsersResponseDto findAll(Pageable pageable);
}
