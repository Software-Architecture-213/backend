package com.example.brandservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY)
    private List<Promotion> promotions;
}
