package com.alex.excel.users.controller;

import com.alex.excel.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/upload-users-data")
    public ResponseEntity<?> uploadUsersData(
            // form-data key must be named as file
            @RequestParam("file") MultipartFile file
    ) {
        userService.massiveUserCreationFromFile(file);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
