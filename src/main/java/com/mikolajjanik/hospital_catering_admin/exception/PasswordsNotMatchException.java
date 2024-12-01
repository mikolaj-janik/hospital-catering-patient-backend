package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class PasswordsNotMatchException extends IOException {
    public PasswordsNotMatchException() {
        super("Passwords do not match! ");
    }
}
