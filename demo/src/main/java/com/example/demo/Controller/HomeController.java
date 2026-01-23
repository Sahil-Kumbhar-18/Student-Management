package com.example.demo.Controller;

import com.example.demo.Repository.*;
import com.example.demo.Security.JWT.JwtUtil;
import com.example.demo.domain.*;
import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserSignupDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class HomeController {
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

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupDto dto) {
        try {
            String message = userService.signup(dto);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        try {
            return ResponseEntity.ok(userService.login(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping("/admin")
    public String admin() {
        return "Welcome ADMIN";
    }

    @GetMapping("/student")
    public String student() {
        return "Welcome STUDENT";
    }

    @GetMapping("/teacher")
    public String teacher() {
        return "Welcome TEACHER";
    }
}
