package com.example.brandservice.dto.response;

import com.example.brandservice.model.GPS;
import lombok.Data;

@Data
public class BrandResponse {
    private String id;
    private String email;
    private String name;
    private String field;
    private String address;
    private GPS gps;
    private String status;
}
