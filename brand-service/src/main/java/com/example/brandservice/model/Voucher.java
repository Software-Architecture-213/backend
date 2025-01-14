package com.example.brandservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "vouchers", indexes = {
        @Index(name = "idx_code", columnList = "code"),
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String code;

   @Enumerated(EnumType.STRING)
    VoucherType type;
    String imageUrl;

   @Enumerated(EnumType.STRING)
    VoucherValue valueType;

    Double value;
    String description;

    @Column(name = "expiredAt")
    LocalDateTime expiredAt;

   @Enumerated(EnumType.STRING)
    VoucherStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "promotionId", referencedColumnName = "id")
    Promotion promotion;

    Integer maxCounts;
    Integer createCounts;

    @Column(name = "createdAt")
    LocalDateTime createAt;
    @Column(name = "updatedAt")
    LocalDateTime updateAt;

    public enum VoucherType {
        ONLINE,
        OFFLINE
    }

    public enum VoucherStatus {
        ACTIVE,
        EXPIRED,
        INACTIVE
    }

    public enum VoucherValue {
        FIXED,
        PERCENTAGE,
        ITEM,
        FREE
    }
}