package com.example.brandservice.dto.response;


import com.example.brandservice.model.Brand;
import com.example.brandservice.model.Voucher;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PromotionResponse {
    private String id;
    private String brandId;
    private String name;
    private String category;
    private String imageUrl;
    private int numOfVouchers;
    private int remainingVouchers;
    private Date startDate;
    private Date endDate;
    private String status;
    private List<Voucher> vouchers;
}