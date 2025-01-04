package com.example.brandservice.dto.request;

import com.example.brandservice.model.Brand;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest {
    String displayName;
    String imageUrl;
    String username; // email
    String password;
    String field;
    Brand.GPS gps;
    Brand.BrandStatus status;
}