package com.example.demo.dto;

import com.example.demo.domain.role;
import lombok.Data;

@Data
public class UserSignupDto {
    private String fullname;
    private String username;
    private String password;
    private role role;
}
