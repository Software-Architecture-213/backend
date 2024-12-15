package com.example.brandservice.dto.response;


import com.example.brandservice.model.Brand;
import lombok.Data;

import java.util.Date;

@Data
public class PromotionResponse {
    private String id;
    private String brandId;
    private String name;
    private String imageUrl;
    private int numOfVouchers;
    private Date startDate;
    private Date endDate;
    private String status;
}