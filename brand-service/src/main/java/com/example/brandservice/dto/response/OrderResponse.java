package com.example.brandservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderResponse {
    private String orderId;
    private String brandName;
    private String brandImageUrl;
    private  LocalDateTime createdAt;
    private  String currency;
    private  double amount;

}

