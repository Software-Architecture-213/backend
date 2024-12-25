package com.example.brandservice.controller;

import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.service.PaypalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.sdk.models.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final ObjectMapper objectMapper;
    private final PaypalService paypalService;

    public CheckoutController(ObjectMapper objectMapper, PaypalService paypalService) {
        this.objectMapper = objectMapper;
        this.paypalService = paypalService;
    }

    @PublicEndpoint
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> request) {
        try {
            String cart = objectMapper.writeValueAsString(request.get("cart"));
            Order response = paypalService.createOrder(cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PublicEndpoint
    @PostMapping("/{orderID}/capture")
    public ResponseEntity<Order> captureOrder(@PathVariable String orderID) {
        try {
            Order response = paypalService.captureOrders(orderID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}