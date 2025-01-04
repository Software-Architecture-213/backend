package com.example.brandservice.service;

import com.example.brandservice.client.PaypalClient;
import com.example.brandservice.dto.request.CartRequest;
import com.example.brandservice.repository.OrderRepository;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.models.Capture;
import com.paypal.sdk.models.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        Order captureOrder = paypalClient.captureOrders(orderID);
        com.example.brandservice.model.Order order = orderRepository.findByOrderId(orderID);
        order.setCreateAt(LocalDateTime.now());
        String amount = captureOrder.getPurchaseUnits().getFirst().getPayments().getCaptures().getFirst().getAmount().getValue();
        String currency = captureOrder.getPurchaseUnits().getFirst().getPayments().getCaptures().getFirst().getAmount().getCurrencyCode();
        order.setAmount(Double.parseDouble(amount));
        order.setCurrency(currency);

        return captureOrder;
    }

    public List<com.example.brandservice.model.Order> getAllOrders(String brandId) throws IOException, ApiException {
        return orderRepository.findAllByBrandId(brandId);
    }

}