package com.example.z7I.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.z7I.model.TestRegistration;

public interface TestRegistrationRepository extends JpaRepository<TestRegistration, Long> {    
    @Query("SELECT tr FROM TestRegistration tr WHERE tr.registrationId = :registrationId")
    TestRegistration findByRegistrationId(@Param("registrationId") String registrationId);
}
