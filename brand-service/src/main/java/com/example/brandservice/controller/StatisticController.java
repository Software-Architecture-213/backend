package com.example.brandservice.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.brandservice.client.GameClient;
import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.dto.response.AdminStatisticResponse.AdminBrandStatisticResponse;
import com.example.brandservice.dto.response.AdminStatisticResponse.AdminGameStatisticResponse;
import com.example.brandservice.dto.response.AdminStatisticResponse.AdminUserStatisticByPromotionResponse;
import com.example.brandservice.dto.response.BrandStatisticResponse.BrandBudgetStatisticResponse;
import com.example.brandservice.dto.response.BrandStatisticResponse.BrandVoucherStatisticResponse;
import com.example.brandservice.service.BrandService;
import com.example.brandservice.service.StatisticService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/statistic")
@AllArgsConstructor
public class StatisticController {
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
  public AdminBrandStatisticResponse getAdminBrandStatistic(@Param("startDate") String startDate,
      @Param("endDate") String endDate) {
    // AdminBrandStatisticResponse statistic =
    // brandService.getAdminBrandStatistic();
    AdminBrandStatisticResponse statistic = statisticService.getAdminBrandStatistic(startDate, endDate);

    return statistic;
  }

  // Endpoint: /brands/:brandId/budget
  @PublicEndpoint
  @GetMapping("/brands/{brandId}/budget")
  public BrandBudgetStatisticResponse getBrandBudgetStatistic(@PathVariable String brandId) {
    BrandBudgetStatisticResponse statistic = gameClient.getBrandBudgetStatisticResponse(null, brandId);

    return statistic;
  }

  @PublicEndpoint
  @GetMapping("/brands/{brandId}/vouchers")
  public BrandVoucherStatisticResponse getBrandVoucherStatistic(@PathVariable String brandId,
      @RequestParam String startDate,
      @RequestParam String endDate) {
    BrandVoucherStatisticResponse statistic = statisticService.getBrandVoucherStatisticResponse(brandId, startDate,
        endDate);

    return statistic;
  }
}
