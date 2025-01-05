package com.example.brandservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "brands")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String displayName;
    String imageUrl;
    String username;
    String password;
    String field;
    @Embedded
    GPS gps;
    // active / inactive

   @Enumerated(EnumType.STRING)
    BrandStatus status;

    @Column(name = "createdAt")
    LocalDateTime createAt;
    @Column(name = "updatedAt")
    LocalDateTime updateAt;

    @Embeddable
    @Data
    public static class GPS {
        Double latitude;
        Double longitude;
    }

    public enum BrandStatus {
        ACTIVE,
        INACTIVE
    }

}



