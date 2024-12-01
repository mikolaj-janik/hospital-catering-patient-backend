package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class MealCannotBeDeletedException extends IOException {
    public MealCannotBeDeletedException() {
        super("Meal cannot be deleted because it does exist in the diary");
    }
}
