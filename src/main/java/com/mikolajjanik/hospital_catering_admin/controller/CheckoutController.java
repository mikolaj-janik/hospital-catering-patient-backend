package com.mikolajjanik.hospital_catering_admin.controller;

import com.mikolajjanik.hospital_catering_admin.dto.NewOrderDTO;
import com.mikolajjanik.hospital_catering_admin.dto.PaymentInfoDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Order;
import com.mikolajjanik.hospital_catering_admin.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoDTO paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @GetMapping("check")
    public ResponseEntity<Void> checkAddingToCartPossibility(@RequestParam("mealId") Long mealId,
                                                             @RequestParam("date") String date) {
        checkoutService.checkAddingToCardPossibility(mealId, date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("order")
    public ResponseEntity<Order> placeOrder(@RequestBody @Valid NewOrderDTO newOrderDTO) {
        Order order = checkoutService.placeOrder(newOrderDTO);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
