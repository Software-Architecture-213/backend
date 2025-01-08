package com.example.brandservice.dto.response;

import java.util.List;

class AdminBrandStatisticObject {
  String id;
  Integer brandCount;
}

public class AdminBrandStatisticResponse {
  List<AdminBrandStatisticObject> data;
}
