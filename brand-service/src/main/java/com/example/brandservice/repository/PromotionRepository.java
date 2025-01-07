package com.example.brandservice.repository;

import com.example.brandservice.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
    List<Promotion> findByBrandId(String brandId);

    @Query("SELECT p FROM Promotion p WHERE p.startDate BETWEEN :startDate AND :endDate")
    List<Promotion> findPromotionsStartingSoon(@Param("startDate") java.time.LocalDateTime startDate,
                                               @Param("endDate") java.time.LocalDateTime endDate);

    // Helper method to calculate dates
    default List<Promotion> findPromotionsStartingSoon(int startDays, int endDays) {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        return findPromotionsStartingSoon(now.plusDays(startDays), now.plusDays(endDays));
    }
}
