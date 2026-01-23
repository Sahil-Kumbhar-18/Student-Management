package com.example.demo.Repository;

import com.example.demo.domain.Student;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    boolean existsByRollNo(String roll);
    Optional<Student> findByUser(User user);
    Optional<Student > findByUser_Username(String username);
}
