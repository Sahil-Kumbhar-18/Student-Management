package com.example.demo.Controller;

import com.example.demo.Repository.StudentRepo;
import com.example.demo.domain.Student;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    @PostMapping("/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Student> createMyProfile(
            @RequestBody StudentDto dto,
            Authentication authentication) {

        String username = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudentForLoggedInUser(username, dto));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentResponseDto> getMyProfile(Authentication authentication) {

        String username = authentication.getName();
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(studentService.getStudentProfile(username));
    }

    @PutMapping("/profile/update")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Student> updateMyProfile(@RequestBody StudentDto dto, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(studentService.UpdateStudentProfile(username,dto));
    }
}
