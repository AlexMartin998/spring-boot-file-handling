package com.alex.excel.users.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void massiveUserCreationFromFile(MultipartFile file);

}
