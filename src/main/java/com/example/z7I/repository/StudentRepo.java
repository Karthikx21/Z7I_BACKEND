package com.example.z7I.repository;

import com.example.z7I.model.StudentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<StudentRegistration, Long> {
    Optional<StudentRegistration> findByRegistrationNumber(String registrationNumber);
}
