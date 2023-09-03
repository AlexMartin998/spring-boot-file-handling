package com.alex.excel.users.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserRequestDto {

    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    private String country;
    @NotNull
    private Integer age;
    @NotEmpty
    private String telephone;
}
