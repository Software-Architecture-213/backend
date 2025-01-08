package com.example.brandservice.repository;

import com.example.brandservice.model.FavouritePromotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface FavouritePromotionsRepository extends JpaRepository<FavouritePromotions, String> {
    Optional<FavouritePromotions> findByUserId(UUID userId);
}
