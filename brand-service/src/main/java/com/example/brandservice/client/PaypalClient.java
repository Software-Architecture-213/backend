// PaypalClient.java
package com.example.brandservice.client;

import com.example.brandservice.dto.request.CartRequest;
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
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class PaypalClient {

    private final PaypalServerSdkClient client;

    public PaypalClient(PaypalServerSdkClient paypalSdkClient) {
        this.client = paypalSdkClient;
    }

    public Order createOrder(CartRequest cart) throws IOException, ApiException {

        OrdersCreateInput ordersCreateInput = new OrdersCreateInput.Builder(
                null,
                new OrderRequest.Builder(
                        CheckoutPaymentIntent.CAPTURE,
                        Collections.singletonList(
                                new PurchaseUnitRequest.Builder(
                                        new AmountWithBreakdown.Builder(
                                                "USD",
                                                cart.getPrice())
                                                .build())
                                        .referenceId(cart.getId())
                                        .build())
                        )
                        .build())
                .build();

        OrdersController ordersController = client.getOrdersController();

        ApiResponse<Order> apiResponse = ordersController.ordersCreate(ordersCreateInput);

        return apiResponse.getResult();
    }

    public Order captureOrders(String orderID) throws IOException, ApiException {
        OrdersCaptureInput ordersCaptureInput = new OrdersCaptureInput.Builder(
                orderID,
                null)
                .build();
        OrdersController ordersController = client.getOrdersController();
        ApiResponse<Order> apiResponse = ordersController.ordersCapture(ordersCaptureInput);
        return apiResponse.getResult();
    }
}
