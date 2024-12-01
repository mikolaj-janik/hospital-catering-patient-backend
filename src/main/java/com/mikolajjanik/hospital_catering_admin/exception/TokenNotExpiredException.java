package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class TokenNotExpiredException extends IOException {
    public TokenNotExpiredException() {
        super("Token is not expired therefore cannot be refreshed!");
    }
}
