package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class OrderAlreadyExistsException extends IOException {
    public OrderAlreadyExistsException() {
        super("Meal type you want to add to cart is already among your upcoming orders on this date");
    }
}
