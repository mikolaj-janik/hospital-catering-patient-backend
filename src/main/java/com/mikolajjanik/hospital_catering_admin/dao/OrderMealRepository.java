package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.OrderMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderMealRepository extends JpaRepository<OrderMeal, Long> {
    List<OrderMeal> findOrderMealsByOrderIdOrderByDate(Long orderId);
    List<OrderMeal> findOrderMealsByDate(LocalDate date);
}
