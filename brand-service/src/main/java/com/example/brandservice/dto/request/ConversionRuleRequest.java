package com.example.brandservice.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ConversionRuleRequest {
    private String voucherId;
    private String promotionId;
    private List<ConversionRuleItemRequest> items;

    @Data
    public static class ConversionRuleItemRequest {
        private String itemId;
        private Integer quantity;
    }
}
