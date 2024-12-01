package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class InvalidMealTypeException extends IOException {
    public InvalidMealTypeException() {
        super("Possible type names are only: 'wszystkie', 'śniadanie', 'obiad', 'kolacja'. It also cannot be null");
    }
}
