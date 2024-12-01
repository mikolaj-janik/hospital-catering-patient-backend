package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class IncorrectMealException extends IOException {
    public IncorrectMealException(Long mealId, String type) {
        super("Meal with id: " + mealId + " is not of type: " + type);
    }
}
