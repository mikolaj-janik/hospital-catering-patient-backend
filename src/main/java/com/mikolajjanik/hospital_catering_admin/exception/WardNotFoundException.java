package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class WardNotFoundException extends IOException {
    public WardNotFoundException(Long id) {
        super("No ward found with id: " + id);
    }
}
