package com.example.demo.service;

import com.example.demo.Repository.AdminRepo;
import com.example.demo.Repository.StudentRepo;
import com.example.demo.Repository.TeacherRepo;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.JWT.JwtUtil;
import com.example.demo.domain.*;
import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserSignupDto;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String signup(UserSignupDto dto) {
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("Username is already taken");
        }
        User user = new User();
        user.setFullname(dto.getFullname());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);
        if (dto.getRole() == role.STUDENT) {
            Student student = new Student();
            student.setUser(savedUser);
            studentRepo.save(student);
        }

        if (dto.getRole() == role.ADMIN) {
            Admin admin = new Admin();
            admin.setUser(savedUser);
            adminRepo.save(admin);
        }

        if (dto.getRole() == role.TEACHER) {
            Teacher teacher = new Teacher();
            teacher.setUser(savedUser);
            teacherRepo.save(teacher);
        }
        return "User registered successfully";
    }

    public Map<String, Object> login(UserLoginDto dto) {
        User dbUser = userRepository.findByUsername(dto.getUsername());

        if (dbUser == null || !passwordEncoder.matches(dto.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.GenerateToken(dbUser);

        return Map.of(
                "token", token,
                "role", dbUser.getRole(),
                "username", dbUser.getUsername()
        );
    }
}
