package com.example.brandservice.dto.response;

import com.example.brandservice.model.Promotion;
import com.example.brandservice.model.Voucher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponse {
    String id;
    String name;
    String brandId;
    String imageUrl;
    Date startDate;
    Date endDate;
    double budget;
    double remainingBudget;
    Promotion.PromotionStatus status;
    List<Voucher> vouchers;
    Date createAt;
    Date updateAt;
    List<String> games;
}