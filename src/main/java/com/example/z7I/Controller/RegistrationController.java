package com.example.z7I.Controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.z7I.dto.RegistrationRequest;
import com.example.z7I.dto.RegistrationResponse;
import com.example.z7I.service.TestRegistrationService;

@RestController
@RequestMapping("/api/registration")
@CrossOrigin(origins = {"http://localhost:3000", "https://z7i.in"})
public class RegistrationController {

    private final TestRegistrationService testRegistrationService;

    public RegistrationController(TestRegistrationService testRegistrationService) {
        this.testRegistrationService = testRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerStudent(@RequestBody RegistrationRequest request) {
        RegistrationResponse response = testRegistrationService.registerStudent(request);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/hallticket")
    public ResponseEntity<byte[]> downloadHallTicket(@RequestParam String registrationId) throws IOException {
        byte[] pdfBytes = testRegistrationService.generateHallTicketPdf(registrationId);
        if (pdfBytes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hallticket_" + registrationId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
