package com.example.brandservice.service;

import com.example.brandservice.client.PaypalClient;
import com.example.brandservice.dto.request.CartRequest;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.models.Order;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class PaypalService {

    private final PaypalClient paypalClient;

    public PaypalService(PaypalClient paypalClient) {
        this.paypalClient = paypalClient;
    }

    public Order createOrder(CartRequest cart) throws IOException, ApiException {
        // Delegates the order creation to PaypalClient
        return paypalClient.createOrder(cart);
    }

    public Order captureOrders(String orderID) throws IOException, ApiException {
        // Delegates the order capture to PaypalClient
        return paypalClient.captureOrders(orderID);
    }
}