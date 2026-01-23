package com.example.demo.dto;

import lombok.Data;

@Data
public class TeacherDto {
    private Long userId;
    private String phoneNo;
    private String email;
    private Long departmentId;
}
