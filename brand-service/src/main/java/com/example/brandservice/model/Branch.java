package com.example.brandservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Table(name = "branches")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String address;
    GPS gps;
    boolean isMain;

    @Embeddable
    @Data
    public static class GPS {
        Double lat;
        Double lng;
    }
}