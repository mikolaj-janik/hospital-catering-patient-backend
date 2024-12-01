package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class DietAlreadyExistException extends IOException{
    public DietAlreadyExistException() {
        super("Diet already exists");
    }
}
