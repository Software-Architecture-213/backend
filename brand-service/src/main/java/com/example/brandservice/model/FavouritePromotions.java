package com.example.brandservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "favourite_promotions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavouritePromotions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    UUID userId;

    @OneToMany
    List<Promotion> promotions;
}
