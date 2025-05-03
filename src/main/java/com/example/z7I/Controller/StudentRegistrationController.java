package com.example.z7I.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.z7I.dto.StudentRegistrationRequest;
import com.example.z7I.model.StudentRegistration;
import com.example.z7I.service.StudentRegistrationService;

@RestController
@RequestMapping("/api/student")
public class StudentRegistrationController {
    public StudentRegistrationService studentRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<StudentRegistration> registerStudent(@RequestBody StudentRegistrationRequest request){
        StudentRegistration studentRegistration = studentRegistrationService.registerStudent(request);
        return ResponseEntity.ok(studentRegistration);
    }

}
