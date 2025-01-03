package com.example.brandservice.dto.request;

import com.example.brandservice.model.Promotion;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionRequest {
    String name;
    String description;
    String imageUrl;
    Date startDate;
    Date endDate;
    String brandId;
    double budget;
    Promotion.PromotionStatus status;
    List<String> games;
}