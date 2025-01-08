package com.example.brandservice.service;

import org.springframework.stereotype.Service;

import com.example.brandservice.client.GameClient;
import com.example.brandservice.dto.response.AdminBrandStatisticResponse;
import com.example.brandservice.repository.BrandRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatisticService {
  private final BrandService brandService;
  private final GameClient gameClient;
  private final BrandRepository brandRepository;

  public AdminBrandStatisticResponse getAdminBrandStatistic() {
    return brandRepository.getBrandCreationCount("2024-12-20", "2024-12-30");
  }
}
