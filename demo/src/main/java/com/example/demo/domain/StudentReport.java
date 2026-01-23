package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentReport {
    @Id
    private Long id;
    private String attendance;
    private String grade;
    private String AttendancePercent;
    @OneToOne
    private Student student;
}
