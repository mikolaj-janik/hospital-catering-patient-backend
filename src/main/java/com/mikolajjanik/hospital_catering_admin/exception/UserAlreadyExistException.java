package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class UserAlreadyExistException extends IOException {
    public UserAlreadyExistException() {
        super("User with provided email address already exist");
    }
}
