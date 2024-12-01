package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class HospitalNotFoundException extends IOException {
    public HospitalNotFoundException(Long id) {
        super("No hospital found with id: " + id);
    }
}
