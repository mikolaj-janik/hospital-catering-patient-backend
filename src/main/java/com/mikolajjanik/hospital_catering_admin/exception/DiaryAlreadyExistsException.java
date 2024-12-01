package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;
import java.time.LocalDate;

public class DiaryAlreadyExistsException extends IOException {
    public DiaryAlreadyExistsException(String dietName, LocalDate date) {
        super("Diet '" + dietName + "' already have scheduled diary on date " + date.toString());
    }
}
