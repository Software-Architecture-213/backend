package com.example.brandservice.dto.response;

import lombok.Data;

@Data
public class BrandResponse {
    private String id;
    private String name;
    private String field;
    private String address;
    private Double gpsLat;
    private Double gpsLong;
    private String status;
}
