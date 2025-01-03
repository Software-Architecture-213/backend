package com.example.brandservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "voucher_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    UID userId;

    @ManyToOne
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    Voucher voucher;

    String qrCode;

    @Enumerated(EnumType.STRING)
    VoucherUserStatus status;

    Date redeemedAt;

    @Column(name = "created_at")
    LocalDateTime createAt;
    @Column(name = "updated_at")
    LocalDateTime updateAt;

    public enum VoucherUserStatus{
        ACTIVE,
        REDEEMED,
        EXPIRED
    }
}

