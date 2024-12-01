package com.mikolajjanik.hospital_catering_admin.exception;
import java.io.IOException;

public class DietCannotBeDeletedException extends IOException {
    public DietCannotBeDeletedException() {
        super("Diet cannot be deleted because is has linked meals.");
    }
}
