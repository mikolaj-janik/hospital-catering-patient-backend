package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class IncorrectTypeException extends IOException {
    public IncorrectTypeException() {
        super("Provided type can only be: 'breakfast', 'lunch', 'supper'");
    }
}
