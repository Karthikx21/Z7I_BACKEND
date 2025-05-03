package com.example.z7I.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.z7I.dto.StudentRegistrationRequest;
import com.example.z7I.model.StudentRegistration;
import com.example.z7I.repository.StudentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentRegistrationService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepo repository;
   public  StudentRegistration registerStudent(StudentRegistrationRequest request){
       if (repository.findByRegistrationNumber(request.getRegistrationNumber()).isPresent()) {
           throw new IllegalArgumentException("Student with registration number " + request.getRegistrationNumber() + " already exists.");
       }
       StudentRegistration student = new StudentRegistration();
       student.setRegistrationNumber(request.getRegistrationNumber());
       student.setMobileNumber(request.getMobileNumber());
       student.setFirstName(request.getFirstName());
       student.setMiddleName(request.getMiddleName());
       student.setLastName(request.getLastName());
       student.setDateOfBirth(request.getDateOfBirth());
       student.setGender(request.getGender());
       student.setStream(request.getStream());
       student.setCategory(request.getCategory());
       student.setTestDateDay(request.getTestDateDay());
       student.setTestDateMonth(request.getTestDateMonth());
       student.setTestDateYear(request.getTestDateYear());
       student.setRegistrationCentreCode(request.getRegistrationCentreCode());
       student.setStudyCentreCode(request.getStudyCentreCode());
       student.setTestCentreCode(request.getTestCentreCode());
       student.setClassPresentlyStudying(request.getClassPresentlyStudying());
       student.setEmail(request.getEmail() != null ? objectMapper.convertValue(request.getEmail(), java.util.Map.class) : null);
       student.setMobile(request.getMobile() != null ? objectMapper.convertValue(request.getMobile(), java.util.Map.class) : null);
       student.setPreferredEmail(request.getPreferredEmail());
       student.setPreferredMobile(request.getPreferredMobile());
       student.setAddress(request.getAddress());
       student.setSchool(request.getSchool());
       student.setParents(request.getParents());
       student.setAchievements(request.getAchievements());
       student.setSiblings(request.getSiblings());
       student.setCreatedAt(java.time.LocalDateTime.now());

       return repository.save(student);
   }}
