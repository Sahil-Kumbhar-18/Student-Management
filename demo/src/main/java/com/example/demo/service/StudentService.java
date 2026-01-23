package com.example.demo.service;

import com.example.demo.Repository.DepartmentRepo;
import com.example.demo.Repository.StudentRepo;
import com.example.demo.Repository.UserRepository;
import com.example.demo.domain.*;
import com.example.demo.dto.AddressDto;
import com.example.demo.dto.StudentDto;

import com.example.demo.dto.StudentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Transactional
    public Student createStudentForLoggedInUser(
            String username, StudentDto dto) {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getRole() != role.STUDENT) {
            throw new RuntimeException("Access denied");
        }

        Student student = studentRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not initialized"));

        if (student.getRollNo() != null) {
            throw new RuntimeException("Profile already created");
        }

        Department dept = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setRollNo(dto.getRollNo());
        student.setDob(dto.getDob());
        student.setGender(dto.getGender());
        student.setDepartment(dept);

        if (dto.getAddress() != null) {
            Address address = new Address();
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());

            address.setStudent(student);
            student.setAddress(address);
        }

        return studentRepo.save(student);
    }
    @Transactional
    public StudentResponseDto getStudentProfile(String username) {
        User user = userRepository.findByUsername(username);
        Student student = studentRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not initialized"));
        Address address=new Address();
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setRollNo(student.getRollNo());
        studentResponseDto.setUsername(username);
        studentResponseDto.setGender(student.getGender());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setPhoneNumber(student.getPhoneNumber());
        studentResponseDto.setDepartmentName(student.getDepartment().getName());
        AddressDto addressDto=new AddressDto();
        addressDto.setStreet(student.getAddress().getStreet());
        addressDto.setCity(student.getAddress().getCity());
        addressDto.setState(student.getAddress().getState());
        studentResponseDto.setAddress(addressDto);
        return studentResponseDto;
    }

    @Transactional
    public Student UpdateStudentProfile(
            String username, StudentDto dto) {
        User user = userRepository.findByUsername(username);
        Student student = studentRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Department dept = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setRollNo(dto.getRollNo());
        student.setDob(dto.getDob());
        student.setGender(dto.getGender());
        student.setDepartment(dept);

        if (dto.getAddress() != null) {
            Address address = student.getAddress();

            if (address == null) {
                address = new Address();
                address.setStudent(student);
            }

            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());

            student.setAddress(address);
        }
        return student;
    }
}
