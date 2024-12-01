package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class BadLoginCredentialsException extends IOException {
    public BadLoginCredentialsException() {
        super("Provided email or password is incorrect.");
    }
}
