package com.mikolajjanik.hospital_catering_admin.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private String message;

    private int status;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "\"message\": "  + "\"" + message + "\"," +
                "\"status\": " + status +
                "}";
    }
}
