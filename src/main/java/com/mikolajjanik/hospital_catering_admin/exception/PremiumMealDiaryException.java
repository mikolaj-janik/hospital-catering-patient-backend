package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class PremiumMealDiaryException extends IOException {
    public PremiumMealDiaryException() {
        super("Premium meal should not be included in basic diary");
    }
}
