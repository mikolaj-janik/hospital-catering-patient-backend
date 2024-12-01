package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class UserNotFoundException extends IOException {
    public UserNotFoundException(String email) {
        super("User with email " + email + " does not exist");
    }
}
