package com.example.brandservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "vouchers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String code;

//    @Enumerated(EnumType.STRING)
    VoucherType type;
    String imageUrl;

//    @Enumerated(EnumType.STRING)
    VoucherValue valueType;

    Double value;
    String description;

    @Column(name = "expired_at")
    LocalDateTime expiredAt;

//    @Enumerated(EnumType.STRING)
    VoucherStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "promotion_id", referencedColumnName = "id")
    Promotion promotion;

    Integer maxCounts;
    Integer createCounts;

    @Column(name = "created_at")
    LocalDateTime createAt;
    @Column(name = "updated_at")
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


