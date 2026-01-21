package com.example.demo.Controller;

import com.example.demo.Repository.*;
import com.example.demo.Security.JWT.JwtUtil;
import com.example.demo.domain.*;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        if (user.getRole() == role.STUDENT) {
            Student student = new Student();
            student.setUser(savedUser);
            studentRepo.save(student);
        }

        if (user.getRole() == role.ADMIN) {
            Admin admin = new Admin();
            admin.setUser(savedUser);
            adminRepo.save(admin);
        }

        if (user.getRole() == role.TEACHER) {
            Teacher teacher = new Teacher();
            teacher.setUser(savedUser);
            teacherRepo.save(teacher);
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {

        User dbUser = userRepository.findByUsername(loginUser.getUsername());

        if (dbUser == null ||
                !passwordEncoder.matches(loginUser.getPassword(), dbUser.getPassword())) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        // Pass full User object
        String token = jwtUtil.GenerateToken(dbUser);

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "role", dbUser.getRole(),
                        "username", dbUser.getUsername()
                )
        );
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
