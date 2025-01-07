package com.example.brandservice.dto.request;

import com.example.brandservice.model.Brand;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest {
    String username;
    String displayName;
    String password;
    String field;
    Brand.GPS gps;
}