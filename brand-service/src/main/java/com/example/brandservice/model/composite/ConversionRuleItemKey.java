package com.example.brandservice.model.composite;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
public class ConversionRuleItemKey implements Serializable {
    String conversionRuleId;
    String itemId;

    public ConversionRuleItemKey() {

    }
}
