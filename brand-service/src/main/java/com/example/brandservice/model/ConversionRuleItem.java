package com.example.brandservice.model;

import com.example.brandservice.model.composite.ConversionRuleItemKey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "conversion_rule_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversionRuleItem {

    @EmbeddedId
    ConversionRuleItemKey id;

    @Column(nullable = false)
    Integer quantity;
}
