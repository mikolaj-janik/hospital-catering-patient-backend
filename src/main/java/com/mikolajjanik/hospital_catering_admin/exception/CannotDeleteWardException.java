package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class CannotDeleteWardException extends IOException {
    public CannotDeleteWardException(String message) {
        super(message);
    }
}
