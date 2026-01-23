package com.example.demo.service;

import com.example.demo.Repository.AdminRepo;
import com.example.demo.Repository.UserRepository;
import com.example.demo.domain.Admin;
import com.example.demo.domain.User;
import com.example.demo.domain.role;
import com.example.demo.dto.AdminDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private UserRepository userRepository;

    public void createAdmin(AdminDto dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != role.TEACHER) {
            throw new RuntimeException("User role must be STUDENT");
        }
        Admin admin = new Admin();
        admin.setEmail(dto.getEmail());
        admin.setUser(user);

       adminRepo.save(admin);
    }
}
