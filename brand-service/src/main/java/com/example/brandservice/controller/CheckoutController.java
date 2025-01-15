package com.example.brandservice.controller;

import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.dto.request.CartRequest;
import com.example.brandservice.dto.response.OrderResponse;
import com.example.brandservice.service.PaypalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.models.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<Order> createOrder(@RequestBody CartRequest request) {
        try {
            Order response = paypalService.createOrder(request);
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

    @GetMapping()
    public ResponseEntity<List<com.example.brandservice.model.Order>> getMyAllOrders(Authentication authentication) {
        try {
            String brandId = (String) authentication.getPrincipal();
            List<com.example.brandservice.model.Order> response = paypalService.getMyAllOrders(brandId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException | ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize( "hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        try {
            List<OrderResponse> response = paypalService.getAllOrders();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException | ApiException e) {
            throw new RuntimeException(e);
        }
    }
}