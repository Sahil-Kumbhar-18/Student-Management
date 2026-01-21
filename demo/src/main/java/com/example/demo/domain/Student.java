package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String phoneNumber;
    private String rollNo;
    private Date dob;
    private String gender;
    @Enumerated(EnumType.STRING)
    private role Srole = role.STUDENT;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentReport report;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private Address address;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
