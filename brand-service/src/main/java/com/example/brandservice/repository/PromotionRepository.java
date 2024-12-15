package com.example.brandservice.repository;

import com.example.brandservice.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
    List<Promotion> findByBrandId(String brandId);
}
