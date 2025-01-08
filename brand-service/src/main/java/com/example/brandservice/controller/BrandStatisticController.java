package com.example.brandservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.brandservice.client.GameClient;
import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.dto.response.AdminBrandStatisticResponse;
import com.example.brandservice.dto.response.AdminGameStatisticResponse;
import com.example.brandservice.dto.response.AdminUserStatisticByPromotionResponse;
import com.example.brandservice.service.BrandService;
import com.example.brandservice.service.StatisticService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/statistic")
@AllArgsConstructor
public class BrandStatisticController {
  private final GameClient gameClient;
  private final StatisticService statisticService;

  @PublicEndpoint
  @GetMapping("/admin/games")
  public AdminGameStatisticResponse getAdminGameStatistic() {
    AdminGameStatisticResponse statistic = gameClient.getAdminGameStatistic(null);

    return statistic;
  }
  
  @PublicEndpoint
  @GetMapping("/admin/users")
  public AdminUserStatisticByPromotionResponse getAdminUserStatistic(@RequestParam String promotionId,
      @RequestParam String type, @RequestParam String startDate, @RequestParam String endDate) {
    AdminUserStatisticByPromotionResponse statistic = gameClient.getAdminUserStatisticByPromotion(null, promotionId,
        startDate, endDate);

    return statistic;
  }
  
  @PublicEndpoint
  @GetMapping("/admin/brands")
  public AdminBrandStatisticResponse getAdminBrandStatistic() {
    // AdminBrandStatisticResponse statistic = brandService.getAdminBrandStatistic();
    AdminBrandStatisticResponse statistic = statisticService.getAdminBrandStatistic();

    return statistic;
  }
}
