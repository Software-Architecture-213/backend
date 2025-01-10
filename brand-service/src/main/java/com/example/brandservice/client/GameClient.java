package com.example.brandservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.brandservice.dto.response.AdminStatisticResponse.AdminGameStatisticResponse;
import com.example.brandservice.dto.response.AdminStatisticResponse.AdminUserStatisticByPromotionResponse;
import com.example.brandservice.dto.response.BrandStatisticResponse.BrandBudgetStatisticResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameClient {
  @Value("${service.game-url}")
  private String GAME_URL;

  private final RestTemplate restTemplate;

  public AdminGameStatisticResponse getAdminGameStatistic(String token) {
    String url = UriComponentsBuilder.fromUriString(GAME_URL)
        .path("/statistics/admin/games")
        .toUriString();

    // Put the token in the header if exists
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", token);

    HttpEntity<String> entity = new HttpEntity<>("body", headers);

    // Send the GET request with the token in the header
    ResponseEntity<AdminGameStatisticResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
        AdminGameStatisticResponse.class);

    return response.getBody();
  }

  public AdminUserStatisticByPromotionResponse getAdminUserStatisticByPromotion(String token, String promotionId,
      String startDate, String endDate) {
    String url = GAME_URL + "/statistics/admin/promotions/" + promotionId + "/users?type=interval&startDate="
        + startDate
        + "&endDate=" + endDate;

    // Put the token in the header if exists
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", token);

    HttpEntity<String> entity = new HttpEntity<>("body", headers);

    // Send the GET request with the token in the header
    ResponseEntity<AdminUserStatisticByPromotionResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
        AdminUserStatisticByPromotionResponse.class);

    return response.getBody();
  }

  public BrandBudgetStatisticResponse getBrandBudgetStatisticResponse(String token, String brandId) {
    String url = GAME_URL + "/statistics/brands/" + brandId + "/budget";

    // Put the token in the header if exists
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", token);

    HttpEntity<String> entity = new HttpEntity<>("body", headers);

    // Send the GET request with the token in the header
    ResponseEntity<BrandBudgetStatisticResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
        BrandBudgetStatisticResponse.class);

    return response.getBody();
  }
}
