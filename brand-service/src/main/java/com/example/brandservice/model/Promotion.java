package com.example.brandservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@Table(name = "promotions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    Brand brand;
    String name;
    String imageUrl;
    int numOfVouchers;
    Date startDate;
    Date endDate;

    String status;
}
