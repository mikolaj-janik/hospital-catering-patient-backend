package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.OrderRepository;
import com.mikolajjanik.hospital_catering_admin.dao.PatientRepository;
import com.mikolajjanik.hospital_catering_admin.entity.Order;
import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import com.mikolajjanik.hospital_catering_admin.exception.PatientNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, PatientRepository patientRepository) {
        this.orderRepository = orderRepository;
        this.patientRepository = patientRepository;
    }
    @Override
    @SneakyThrows
    public List<Order> getOrdersByPatientId(Long patientId) {
        Patient patient = patientRepository.findPatientById(patientId);

        if (patient == null) {
            throw new PatientNotFoundException(patientId);
        }

        return this.orderRepository.getOrdersByPatientIdOrderByOrderDateDesc(patientId);
    }
}
