package com.example.brandservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

public class BrandStatisticResponse {
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class BrandBudgetStatisticObject {
    String id;
    String name;
    Double budget;
    Double remainingBudget;
    Double spentBudget;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class BrandBudgetStatisticResponse {
    List<BrandBudgetStatisticObject> data;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class BrandVoucherStatisticObject {
    String id;
    Integer voucherCount;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
  public static class BrandVoucherStatisticResponse {
    List<BrandVoucherStatisticObject> data;
  }
}
