package com.example.brandservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
class AdminGameStatisticObject {
  String id;
  String name;
  String playCount;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AdminGameStatisticResponse {
  List<AdminGameStatisticObject> data;
}

