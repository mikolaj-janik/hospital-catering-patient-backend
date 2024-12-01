package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class CannotDeleteDieticianException extends IOException {
    public CannotDeleteDieticianException(Long id) {
        super("Cannot delete dietician with id: " + id + ", because they have connected wards");
    }
}
