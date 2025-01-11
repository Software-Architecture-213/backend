package com.example.brandservice.client;

import com.example.brandservice.dto.response.ValidatedTokenResponse;
import com.example.brandservice.model.Promotion;
import com.example.brandservice.utility.DateUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GameClient {
    @Value("${service.game-url}")
    private String GAME_URL;

    private final RestTemplate restTemplate;

    public void createPromotions(Promotion promotion) {
        String url = UriComponentsBuilder.fromUriString(GAME_URL)
                .path("promotions/")
                .toUriString();
        Map<String, Object> promotionMap = new HashMap<>();

        promotionMap.put("id", promotion.getId());
        promotionMap.put("name", promotion.getName());
        promotionMap.put("description", promotion.getDescription());
        promotionMap.put("imageUrl", promotion.getImageUrl());
        promotionMap.put("startDate", promotion.getStartDate());
        promotionMap.put("endDate", promotion.getEndDate());
        promotionMap.put("brandId", promotion.getBrand() != null ? promotion.getBrand().getId() : null);
        promotionMap.put("budget", promotion.getBudget());
        promotionMap.put("remainingBudget", promotion.getRemainingBudget());
        promotionMap.put("status", promotion.getStatus() != null ? promotion.getStatus().toString().toLowerCase() : null);
        promotionMap.put("createdAt", Date.from(promotion.getCreateAt().atZone(ZoneId.systemDefault()).toInstant()));

        var response = restTemplate.postForObject(url, promotionMap, Object.class);
    }

    public Object getGameById(String gameId){
        String url = UriComponentsBuilder.fromUriString(GAME_URL)
                .path("/{id}")
                .buildAndExpand(gameId)
                .toUriString();
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch game details: " + e.getMessage(), e);
        }
    }

    public Object getGameByPromotionId(String promotionId){
        String url = UriComponentsBuilder.fromUriString(GAME_URL)
                .path("/promotions/{id}")
                .buildAndExpand(promotionId)
                .toUriString();
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch game details: " + e.getMessage(), e);
        }
    }
}
