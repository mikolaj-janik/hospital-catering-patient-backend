package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class DietMismatchException extends IOException {
    public DietMismatchException(Long dietId, Long mealId) {
        super("Diet with id " + dietId + " is not a diet of meal with id " + mealId);
    }
}
