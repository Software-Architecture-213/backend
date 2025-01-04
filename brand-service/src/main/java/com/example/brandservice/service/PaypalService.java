package com.example.brandservice.service;

import com.example.brandservice.client.PaypalClient;
import com.example.brandservice.dto.request.CartRequest;
import com.example.brandservice.repository.OrderRepository;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.models.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@AllArgsConstructor

public class PaypalService {

    private final PaypalClient paypalClient;
    private final OrderRepository orderRepository;

    public Order createOrder(CartRequest cart) throws IOException, ApiException {
        Order order = paypalClient.createOrder(cart);
        com.example.brandservice.model.Order orderModel = new com.example.brandservice.model.Order();
        orderModel.setBrandId(cart.getId());
        orderModel.setOrderId(order.getId());
        orderRepository.save(orderModel);
        return order;
    }

    public Order captureOrders(String orderID) throws IOException, ApiException {
        // Delegates the order capture to PaypalClient
        return paypalClient.captureOrders(orderID);
    }


}