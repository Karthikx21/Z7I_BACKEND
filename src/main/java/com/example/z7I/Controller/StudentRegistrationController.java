package com.example.z7I.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.z7I.dto.StudentRegistrationRequest;
import com.example.z7I.model.StudentRegistration;
import com.example.z7I.service.StudentRegistrationService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentRegistrationController {

    private final StudentRegistrationService studentRegistrationService;

    public StudentRegistrationController(StudentRegistrationService studentRegistrationService) {
        this.studentRegistrationService = studentRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<StudentRegistration> registerStudent(@RequestBody StudentRegistrationRequest request) {
        StudentRegistration studentRegistration = studentRegistrationService.registerStudent(request);
        return ResponseEntity.ok(studentRegistration);
    }

    // Optional: Health check endpoint for Railway
    @RequestMapping("/health")
    public String health() {
        return "✅ Student Registration API is up.";
    }
}

