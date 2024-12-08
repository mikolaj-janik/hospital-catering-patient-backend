package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.CheckoutRepository;
import com.mikolajjanik.hospital_catering_admin.dao.MealRepository;
import com.mikolajjanik.hospital_catering_admin.dao.OrderMealRepository;
import com.mikolajjanik.hospital_catering_admin.dao.PatientRepository;
import com.mikolajjanik.hospital_catering_admin.dto.CartItem;
import com.mikolajjanik.hospital_catering_admin.dto.OrderDTO;
import com.mikolajjanik.hospital_catering_admin.dto.PaymentInfoDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import com.mikolajjanik.hospital_catering_admin.entity.Order;
import com.mikolajjanik.hospital_catering_admin.entity.OrderMeal;
import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import com.mikolajjanik.hospital_catering_admin.exception.MealNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.PatientNotFoundException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final OrderMealRepository orderMealRepository;
    private final PatientRepository patientRepository;
    private final MealRepository mealRepository;

    @Autowired
    public CheckoutServiceImpl(@Value("${stripe.key.secret}") String secretKey,
                               CheckoutRepository checkoutRepository,
                               OrderMealRepository orderMealRepository,
                               PatientRepository patientRepository,
                               MealRepository mealRepository) {
        Stripe.apiKey = secretKey;
        this.checkoutRepository = checkoutRepository;
        this.orderMealRepository = orderMealRepository;
        this.patientRepository = patientRepository;
        this.mealRepository = mealRepository;
    }
    @Override
    public PaymentIntent createPaymentIntent(PaymentInfoDTO paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);
    }

    @Override
    @SneakyThrows
    public Order placeOrder(OrderDTO orderDTO) {
        List<CartItem> cartItems = orderDTO.getCartItems();
        LocalDateTime date = LocalDateTime.now();
        Long patientId = orderDTO.getPatient().getId();

        Patient patient = patientRepository.findPatientById(patientId);

        if (patient == null) {
            throw new PatientNotFoundException(patientId);
        }

        Order order = new Order();

        order.setPatient(patient);
        order.setOrderDate(date);
        order.setTotalPrice(orderDTO.getTotalPrice());

        order = checkoutRepository.save(order);

        Order finalOrder = order;
        for (CartItem cartItem : cartItems) {
            OrderMeal orderMeal = new OrderMeal();
            orderMeal.setOrder(finalOrder);

            Long mealId = cartItem.getMeal().getId();
            Meal meal = mealRepository.findMealById(mealId);

            if (meal == null) throw new MealNotFoundException(mealId);

            orderMeal.setMeal(meal);
            orderMeal.setDate(cartItem.getDate());

            orderMealRepository.save(orderMeal);
        }

        return finalOrder;
    }
}
