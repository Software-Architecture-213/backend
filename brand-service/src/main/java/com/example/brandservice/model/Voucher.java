    package com.example.brandservice.model;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import jakarta.persistence.*;
    import lombok.AccessLevel;
    import lombok.Data;
    import lombok.experimental.FieldDefaults;

    import java.util.Date;

    @Entity
    @Data
    @Table(name = "vouchers")
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class Voucher {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference
        @JoinColumn(name = "promotion_id", referencedColumnName = "id")
        Promotion promotion;

        // Thông tin về voucher hoặc trò chơi
        String voucherCode;
        String qrCode;
        String imageUrl;
        Double value;
        String description;
        Date expirationDate;
        String status;
    }
