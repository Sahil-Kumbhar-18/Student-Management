package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Enumerated(EnumType.STRING)
    private role Trole = role.STUDENT;

    @OneToOne
    private Address address;

    @ManyToOne
    private Department department;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
