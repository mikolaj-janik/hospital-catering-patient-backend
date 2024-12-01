package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class TokenNotFoundException extends IOException {
    public TokenNotFoundException() {
        super("You must provide a JWT token to access this resource.");
    }
}
