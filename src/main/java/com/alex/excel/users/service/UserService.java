package com.alex.excel.users.service;

import com.alex.excel.users.dto.PaginatedUsersResponseDto;
import com.alex.excel.users.dto.UserRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {

    void massiveUserCreationFromFile(MultipartFile file);

    PaginatedUsersResponseDto findAll(Pageable pageable);

    void bulkCreate(List<UserRequestDto> userRequestDtoList);
}
