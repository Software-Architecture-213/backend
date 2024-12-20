package com.example.brandservice.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class GPS {
    Double latitude;
    Double longitude;
}