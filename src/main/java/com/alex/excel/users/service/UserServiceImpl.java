package com.alex.excel.users.service;

import com.alex.excel.users.entity.User;
import com.alex.excel.users.helper.ExcelUploadHelper;
import com.alex.excel.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public void saveExcelUserList(MultipartFile file) {
        if (!ExcelUploadHelper.isValidExcelFile(file)) throw new IllegalArgumentException("Invalid File");

        try {
            List<User> users = ExcelUploadHelper.getUsersDataFromExcel(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException("Invalid Excel File");
        }
    }

}
