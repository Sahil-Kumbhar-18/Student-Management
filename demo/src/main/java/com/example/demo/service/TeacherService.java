package com.example.demo.service;

import com.example.demo.Repository.DepartmentRepo;
import com.example.demo.Repository.TeacherRepo;
import com.example.demo.Repository.UserRepository;
import com.example.demo.domain.*;
import com.example.demo.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    private Teacher createTeacher(TeacherDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != role.TEACHER) {
            throw new RuntimeException("User role must be STUDENT");
        }
        Department department = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Teacher teacher = new Teacher();
        teacher.setPhoneNo(dto.getPhoneNo());
        teacher.setEmail(dto.getEmail());
        teacher.setDepartment(department);
        teacher.setUser(user);

       return teacherRepo.save(teacher);
    }
}
