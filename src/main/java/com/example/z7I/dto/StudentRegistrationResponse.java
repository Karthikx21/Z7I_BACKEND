package com.example.z7I.dto;

public class StudentRegistrationResponse {
    private boolean registered;
    private String message;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
