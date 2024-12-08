package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dto.OrderDTO;
import com.mikolajjanik.hospital_catering_admin.dto.PaymentInfoDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Order;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {
    PaymentIntent createPaymentIntent(PaymentInfoDTO paymentInfo) throws StripeException;
    Order placeOrder(OrderDTO orderDTO);
}
