package com.example.brandservice.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
    private String name;
    private String field;
    private String address;
    private Double gpsLat;
    private Double gpsLong;
    private String status;
}