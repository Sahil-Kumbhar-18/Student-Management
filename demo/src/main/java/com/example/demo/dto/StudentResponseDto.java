package com.example.demo.dto;

import lombok.Data;

@Data
public class StudentResponseDto {
    private Long id;
    private String email;
    private String phoneNumber;
    private String rollNo;
    private String gender;
    private String departmentName;
    private String username;
    private AddressDto address;
}
