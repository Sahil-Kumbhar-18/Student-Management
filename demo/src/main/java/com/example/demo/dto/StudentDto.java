package com.example.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentDto {
    private String email;
    private String phoneNumber;
    private String rollNo;
    private Date dob;
    private String gender;
    private Long departmentId;
    private AddressDto address;
}
