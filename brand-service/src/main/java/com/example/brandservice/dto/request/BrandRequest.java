package com.example.brandservice.dto.request;

import com.example.brandservice.model.GPS;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
    private String email;
    private String password;
    private String name;
    private String field;
    private String address;
    private GPS gps;
    private String status;
}