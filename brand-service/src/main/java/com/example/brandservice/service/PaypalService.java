package com.example.brandservice.service;

import com.example.brandservice.client.PaypalClient;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.controllers.OrdersController;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.http.response.ApiResponse;
import com.paypal.sdk.models.Order;
import com.paypal.sdk.models.OrderRequest;
import com.paypal.sdk.models.OrdersCaptureInput;
import com.paypal.sdk.models.OrdersCreateInput;
import com.paypal.sdk.models.AmountWithBreakdown;
import com.paypal.sdk.models.CheckoutPaymentIntent;
import com.paypal.sdk.models.PurchaseUnitRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class PaypalService {

    private final PaypalClient paypalClient;

    public PaypalService(PaypalClient paypalClient) {
        this.paypalClient = paypalClient;
    }

    public Order createOrder(String cart) throws IOException, ApiException {
        // Delegates the order creation to PaypalClient
        return paypalClient.createOrder(cart);
    }

    public Order captureOrders(String orderID) throws IOException, ApiException {
        // Delegates the order capture to PaypalClient
        return paypalClient.captureOrders(orderID);
    }
}