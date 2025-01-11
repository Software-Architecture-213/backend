package com.example.brandservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

public class AdminStatisticResponse {
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AdminGameStatisticObject {
    String id;
    String name;
    String playCount;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
  public static class AdminGameStatisticResponse {
    List<AdminGameStatisticObject> data;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AdminUserStatisticByPromotionObject {
    String id;
    Integer userCount;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
  public static class AdminUserStatisticByPromotionResponse {
    List<AdminUserStatisticByPromotionObject> data;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AdminBrandStatisticObject {
    String id;
    Integer brandCount;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
  public static class AdminBrandStatisticResponse {
    List<AdminBrandStatisticObject> data;
  }
}
