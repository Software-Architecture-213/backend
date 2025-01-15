package com.example.brandservice.client;

import com.example.brandservice.dto.response.ValidatedTokenResponse;
import com.example.brandservice.dto.response.AdminStatisticResponse.AdminGameStatisticResponse;
import com.example.brandservice.dto.response.AdminStatisticResponse.AdminUserStatisticByPromotionResponse;
import com.example.brandservice.dto.response.BrandStatisticResponse.BrandBudgetStatisticResponse;
import com.example.brandservice.model.Promotion;
import com.example.brandservice.utility.DateUtility;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    private static final Logger log = LoggerFactory.getLogger(GameClient.class);
    @Value("${service.game-url}")
    private String GAME_URL;

    private final RestTemplate restTemplate;

    // Methods from Minh-statistic branch
    public AdminGameStatisticResponse getAdminGameStatistic(String token) {
        String url = UriComponentsBuilder.fromUriString(GAME_URL)
            .path("/statistics/admin/games")
            .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<AdminGameStatisticResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
            AdminGameStatisticResponse.class);

        return response.getBody();
    }

    public AdminUserStatisticByPromotionResponse getAdminUserStatisticByPromotion(String token, String promotionId,
            String startDate, String endDate) {
        String url = GAME_URL + "/statistics/admin/promotions/" + promotionId + "/users?type=interval&startDate="
            + startDate
            + "&endDate=" + endDate;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<AdminUserStatisticByPromotionResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
            AdminUserStatisticByPromotionResponse.class);

        return response.getBody();
    }

    public BrandBudgetStatisticResponse getBrandBudgetStatisticResponse(String token, String brandId) {
        String url = GAME_URL + "/statistics/brands/" + brandId + "/budget";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<BrandBudgetStatisticResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
            BrandBudgetStatisticResponse.class);

        return response.getBody();
    }

    // Methods from master branch
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
        promotionMap.put("status",
                promotion.getStatus() != null ? promotion.getStatus().toString().toLowerCase() : null);
        promotionMap.put("createdAt", Date.from(promotion.getCreateAt().atZone(ZoneId.systemDefault()).toInstant()));

        var response = restTemplate.postForObject(url, promotionMap, Object.class);
        log.info("Promotion create: " + response.toString());
    }

    public Object getGameById(String gameId) {
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

    public Object getItemByPromotionId(String promotionId){
        String url = UriComponentsBuilder.fromUriString(GAME_URL)
                .path("/promotion/{id}")
                .buildAndExpand(promotionId)
                .toUriString();
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch game details: " + e.getMessage(), e);
        }
    }
}