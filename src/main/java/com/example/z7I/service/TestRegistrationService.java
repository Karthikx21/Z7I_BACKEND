package com.example.z7I.service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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

    public byte[] generateHallTicketPdf(String registrationId) {
    TestRegistration reg = testRegistrationRepository.findByRegistrationId(registrationId);
    if (reg == null) {
        return null;
    }
    StudentProfile student = reg.getStudent();
    TestDetails test = reg.getTest();

    Document document = new Document();
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("Hall Ticket"));
        document.add(new Paragraph("Registration Number: " + reg.getRegistrationId()));
        document.add(new Paragraph("Name: " + student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName()));
        document.add(new Paragraph("Address: " + student.getAddressLine1() + ", " + student.getAddressLine2() + ", " + student.getCity() + ", " + student.getState() + ", " + student.getCountry() + ", " + student.getPinCode()));
        document.add(new Paragraph("Email ID: " + student.getEmail()));
        document.add(new Paragraph("Program: " + test.getProgramName()));
        document.add(new Paragraph("Test Centre: " + test.getTestCentre()));
        document.add(new Paragraph("Test Date: " + test.getTestDate().format(DateTimeFormatter.ISO_DATE)));
        // Assuming exam schedule and reporting time are fixed or can be added here as needed
        document.add(new Paragraph("Exam Schedule: 10:00 AM - 1:00 PM"));
        document.add(new Paragraph("Reporting Time: 9:00 AM"));
        document.add(new Paragraph("Registration Date: " + reg.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME)));

        document.close();
        return baos.toByteArray();
    } catch (DocumentException | java.io.IOException e) {
        e.printStackTrace();
        return null;
    }

}
}
