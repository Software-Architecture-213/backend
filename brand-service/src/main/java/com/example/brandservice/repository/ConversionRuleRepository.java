package com.example.brandservice.repository;

import com.example.brandservice.model.ConversionRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversionRuleRepository extends JpaRepository<ConversionRule, String> {
    Optional<ConversionRule> findConversionRuleByPromotionId(String id);
}
