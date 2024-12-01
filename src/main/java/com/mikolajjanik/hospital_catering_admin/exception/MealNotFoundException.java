package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class MealNotFoundException extends IOException {
    public MealNotFoundException(Long id) {
        super("Meal not found with id: " + id);
    }
}
