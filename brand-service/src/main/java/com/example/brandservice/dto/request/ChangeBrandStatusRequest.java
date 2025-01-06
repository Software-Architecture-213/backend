package com.example.brandservice.dto.request;

import com.example.brandservice.model.Brand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeBrandStatusRequest {
    String email;
    Brand.BrandStatus status;
    String message;
}
