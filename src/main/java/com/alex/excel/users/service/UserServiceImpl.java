package com.alex.excel.users.service;

import com.alex.excel.dataexchange.FileDataPersistenceProcessor;
import com.alex.excel.users.dto.PaginatedUsersResponseDto;
import com.alex.excel.users.dto.UserRequestDto;
import com.alex.excel.users.entity.User;
import com.alex.excel.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("ExcelUsersDataProcessor")
    private FileDataPersistenceProcessor<User> excelUsersDataProcessor;

    private final UserRepository userRepository;


    @Override
    public void massiveUserCreationFromFile(MultipartFile file) {
        if (!excelUsersDataProcessor.isValidFile(file)) throw new IllegalArgumentException("Invalid File");

        try {
            List<User> users = excelUsersDataProcessor.processData(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException("Invalid Excel File");
        }
    }

    /*
    @Override
    public void massiveUserCreationFromFile(MultipartFile file) {
        if (!ExcelUploadHelper.isValidExcelFile(file)) throw new IllegalArgumentException("Invalid File");

        try {
            List<User> users = ExcelUploadHelper.getUsersDataFromExcel(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException("Invalid Excel File");
        }
    }
    */


    @Override
    public PaginatedUsersResponseDto findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);

        return PaginatedUsersResponseDto.builder()
                .users(userPage.getContent())
                .pageNumber(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .isLastOne(userPage.isLast())
                .build();
    }

    @Override
    public void bulkCreate(List<UserRequestDto> userRequestDtoList) {
        ModelMapper modelMapper = new ModelMapper();

        List<User> users = userRequestDtoList.stream().map(
                userRequestDto -> modelMapper.map(userRequestDto, User.class)
        ).toList();

        this.userRepository.saveAll(users);
    }

}
