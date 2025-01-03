package com.example.brandservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "promotions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String description;
    String imageUrl;
    Date startDate;
    Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    Brand brand;

    Double budget;
    Double remainingBudget;

//    @Enumerated(EnumType.STRING)
    PromotionStatus status;

    @JsonManagedReference
    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    List<Voucher> vouchers;

    @Column(name = "created_at")
    LocalDateTime createAt;
    @Column(name = "updated_at")
    LocalDateTime updateAt;

    public enum PromotionStatus {
        ACTIVE,
        INACTIVE,
        EXPIRED,
        PAID
    }
}

