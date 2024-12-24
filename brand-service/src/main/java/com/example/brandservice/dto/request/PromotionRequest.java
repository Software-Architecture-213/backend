package com.example.brandservice.dto.request;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionRequest {
    private String brandId;
    private String name;
    private String imageUrl;
    private String category;
    private int numOfVouchers;
    private Date startDate;
    private Date endDate;
    private String status;
}