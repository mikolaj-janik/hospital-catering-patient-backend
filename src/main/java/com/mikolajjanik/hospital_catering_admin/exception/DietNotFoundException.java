package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class DietNotFoundException extends IOException {
    public DietNotFoundException(Long id) {
        super("No diet found with id: " + id);
    }
}
