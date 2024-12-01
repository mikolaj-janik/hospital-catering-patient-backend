package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class UnauthenticatedException extends IOException {
    public UnauthenticatedException() {
        super("The request is unauthenticated.");
    }

}
