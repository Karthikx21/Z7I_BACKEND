package com.example.z7I.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.z7I.dto.RegistrationRequest;
import com.example.z7I.dto.RegistrationResponse;
import com.example.z7I.model.StudentProfile;
import com.example.z7I.model.TestDetails;
import com.example.z7I.model.TestRegistration;
import com.example.z7I.repository.StudentProfileRepository;
import com.example.z7I.repository.TestDetailsRepository;
import com.example.z7I.repository.TestRegistrationRepository;

@Service
public class TestRegistrationService {

    private final TestRegistrationRepository testRegistrationRepository;
    private final TestDetailsRepository testDetailsRepository;
    private final StudentProfileRepository studentProfileRepository;

    public TestRegistrationService(TestRegistrationRepository testRegistrationRepository,
                                   TestDetailsRepository testDetailsRepository,
                                   StudentProfileRepository studentProfileRepository) {
        this.testRegistrationRepository = testRegistrationRepository;
        this.testDetailsRepository = testDetailsRepository;
        this.studentProfileRepository = studentProfileRepository;
    }

    public RegistrationResponse registerStudent(RegistrationRequest request) {
        RegistrationResponse response = new RegistrationResponse();
        if (request == null || request.getTestId() <= 0 || request.getStudentId() <= 0) {
            response.setSuccess(false);
            response.setMessage("Invalid testId or studentId");
            return response;
        }
        Optional<TestDetails> testOpt = testDetailsRepository.findById((long) request.getTestId());
        Optional<StudentProfile> studentOpt = studentProfileRepository.findById((long) request.getStudentId());
        if (testOpt.isEmpty() || studentOpt.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("Test or Student not found");
            return response;
        }
        TestRegistration reg = new TestRegistration();
        reg.setTest(testOpt.get());
        reg.setStudent(studentOpt.get());
        reg.setPaymentStatus(true);
        reg = testRegistrationRepository.save(reg);
        // Generate registrationId in format REGxxx using saved entity's ID
        String regId = String.format("REG%03d", reg.getId());
        reg.setRegistrationId(regId);
        testRegistrationRepository.save(reg);
        response.setSuccess(true);
        response.setRegistrationId(regId);
        response.setMessage("Registration successful");
        return response;
    }
}
