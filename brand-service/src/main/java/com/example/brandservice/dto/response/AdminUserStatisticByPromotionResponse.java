package com.example.brandservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

class AdminUserStatisticByPromotionObject {
  String id;
  Integer userCount;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AdminUserStatisticByPromotionResponse {
  List<AdminUserStatisticByPromotionObject> data;
}
