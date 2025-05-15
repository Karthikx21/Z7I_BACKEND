package com.example.z7I.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.z7I.model.TestRegistration;

public interface TestRegistrationRepository extends JpaRepository<TestRegistration, Long> {
    TestRegistration findByRegistrationId(String registrationId);
}
