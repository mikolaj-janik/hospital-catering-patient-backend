package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class PatientNotFoundException extends IOException {
    public PatientNotFoundException(Long id) {
        super("No patient found with id: " + id);
    }
    public PatientNotFoundException(String login) {
        super("No patient found with pesel: " + login);
    }
}
