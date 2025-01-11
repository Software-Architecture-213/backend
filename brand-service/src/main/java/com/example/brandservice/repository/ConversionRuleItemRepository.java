package com.example.brandservice.repository;

import com.example.brandservice.model.ConversionRuleItem;
import com.example.brandservice.model.composite.ConversionRuleItemKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionRuleItemRepository extends JpaRepository<ConversionRuleItem, ConversionRuleItemKey> {
}