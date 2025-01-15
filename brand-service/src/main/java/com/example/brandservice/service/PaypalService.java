package com.example.brandservice.service;

import com.example.brandservice.client.PaypalClient;
import com.example.brandservice.dto.request.CartRequest;
import com.example.brandservice.dto.response.OrderResponse;
import com.example.brandservice.model.Brand;
import com.example.brandservice.repository.BrandRepository;
import com.example.brandservice.repository.OrderRepository;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.models.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PaypalService {

    private final PaypalClient paypalClient;
    private final OrderRepository orderRepository;
    private final BrandRepository brandRepository;


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
        orderRepository.save(order);
        return captureOrder;
    }

    public List<com.example.brandservice.model.Order> getMyAllOrders(String brandId) throws IOException, ApiException {
        return orderRepository.findAllByBrandId(brandId);
    }

    public List<OrderResponse> getAllOrders() throws IOException, ApiException {
        List<Brand> brands = brandRepository.findAll();
        List<String> brandIds = brands.stream().map(Brand::getId).toList();
        List<com.example.brandservice.model.Order> orders = orderRepository.findAllByBrandIdIn(brandIds);
        Map<String, Brand> brandIdAndBrand = brands.stream().collect(Collectors.toMap(Brand::getId, brand -> brand));
        List<OrderResponse> ordersResponse = new ArrayList<>();
        for (com.example.brandservice.model.Order order : orders) {
            OrderResponse.OrderResponseBuilder builder = OrderResponse.builder()
                    .orderId(order.getOrderId())
                    .amount(order.getAmount())
                    .currency(order.getCurrency())
                    .createdAt(order.getCreateAt());
            if (brandIdAndBrand.containsKey(order.getBrandId())) {
                builder.brandName(brandIdAndBrand.get(order.getBrandId()).getDisplayName())
                        .brandImageUrl(brandIdAndBrand.get(order.getBrandId()).getImageUrl());
                ordersResponse.add(builder.build());
            }
        }
        ordersResponse.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        return ordersResponse;
    }

}