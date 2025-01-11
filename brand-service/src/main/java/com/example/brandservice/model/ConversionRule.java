package com.example.brandservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "conversion_rule")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversionRule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne()
    @JoinColumn(name = "voucherId", referencedColumnName = "id")
    Voucher voucher;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne()
    @JoinColumn(name = "promotionId", referencedColumnName = "id")
    Promotion promotion;

    @Column(name = "createdAt")
    LocalDateTime createAt;
    @Column(name = "updatedAt")
    LocalDateTime updateAt;

    @OneToMany(mappedBy = "id.conversionRuleId", cascade = CascadeType.ALL)
    List<ConversionRuleItem> items;
}
