package com.alex.excel.users.controller;

import com.alex.excel.common.constants.PaginationConstants;
import com.alex.excel.users.dto.PaginatedUsersResponseDto;
import com.alex.excel.users.dto.UserRequestDto;
import com.alex.excel.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<?> bulkCreate(@RequestBody List<UserRequestDto> userRequestDtoList) {
        this.userService.bulkCreate(userRequestDtoList);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/upload-users-data")
    public ResponseEntity<?> uploadUsersData(
            // form-data key must be named as file
            @RequestParam("file") MultipartFile file
    ) {
        userService.massiveUserCreationFromFile(file);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedUsersResponseDto> findAll(
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_SIZE) int size,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_SORT_DIR) String sortDir
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(userService.findAll(pageable));
    }

}
