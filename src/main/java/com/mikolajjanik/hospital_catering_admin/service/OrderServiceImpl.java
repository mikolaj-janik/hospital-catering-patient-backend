package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.OrderMealRepository;
import com.mikolajjanik.hospital_catering_admin.dao.OrderRepository;
import com.mikolajjanik.hospital_catering_admin.dao.PatientRepository;
import com.mikolajjanik.hospital_catering_admin.dto.OrderDTO;
import com.mikolajjanik.hospital_catering_admin.dto.OrderItemDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Order;
import com.mikolajjanik.hospital_catering_admin.entity.OrderMeal;
import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import com.mikolajjanik.hospital_catering_admin.exception.PatientNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PatientRepository patientRepository;
    private final OrderMealRepository orderMealRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            PatientRepository patientRepository,
                            OrderMealRepository orderMealRepository) {
        this.orderRepository = orderRepository;
        this.patientRepository = patientRepository;
        this.orderMealRepository = orderMealRepository;
    }
    @Override
    @SneakyThrows
    public List<OrderDTO> getOrdersByPatientId(Long patientId) {
        Patient patient = patientRepository.findPatientById(patientId);

        if (patient == null) {
            throw new PatientNotFoundException(patientId);
        }

        List<Order> orders = orderRepository.getOrdersByPatientIdOrderByOrderDateDesc(patientId);
        List<OrderDTO> orderDTOS = new ArrayList<>();

        orders.forEach(order -> {

            List<OrderMeal> orderItems = orderMealRepository.findOrderMealsByOrderIdOrderByDate(order.getId());
            List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

            orderItems.forEach(orderItem -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO(orderItem.getMeal(), orderItem.getDate());
                orderItemDTOS.add(orderItemDTO);
            });

            OrderDTO orderDTO = new OrderDTO(
                    order.getId(),
                    order.getPatient(),
                    order.getTotalPrice(),
                    order.getOrderDate(),
                    orderItemDTOS
            );
            orderDTOS.add(orderDTO);
        });

        return orderDTOS;
    }
}
