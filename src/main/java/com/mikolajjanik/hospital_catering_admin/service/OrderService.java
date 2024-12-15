package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersByPatientId(Long patientId);
}
