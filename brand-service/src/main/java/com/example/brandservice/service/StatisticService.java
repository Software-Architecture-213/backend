package com.example.brandservice.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.brandservice.client.GameClient;
import com.example.brandservice.dto.response.AdminStatisticResponse;
import com.example.brandservice.dto.response.AdminStatisticResponse.AdminBrandStatisticObject;
import com.example.brandservice.dto.response.AdminStatisticResponse.AdminBrandStatisticResponse;
import com.example.brandservice.dto.response.BrandStatisticResponse;
import com.example.brandservice.dto.response.BrandStatisticResponse.BrandBudgetStatisticResponse;
import com.example.brandservice.dto.response.BrandStatisticResponse.BrandVoucherStatisticResponse;
import com.example.brandservice.repository.BrandRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatisticService {
  private final BrandService brandService;
  private final GameClient gameClient;
  private final BrandRepository brandRepository;

  public AdminBrandStatisticResponse getAdminBrandStatistic(String startDate, String endDate) {
    // convert startDate and endDate to Date in sql
    Date start = Date.valueOf(startDate);
    Date end = Date.valueOf(endDate);

    List<Object[]> rawResults = brandRepository.getBrandCreationCount(startDate, endDate);
    List<AdminStatisticResponse.AdminBrandStatisticObject> results = rawResults.stream()
        .map(row -> new AdminStatisticResponse.AdminBrandStatisticObject(
            (String) row[0],
            (Integer) row[1]))
        .collect(Collectors.toList());

    AdminStatisticResponse.AdminBrandStatisticResponse response = new AdminStatisticResponse.AdminBrandStatisticResponse();
    response.setData(results);
    return response;
  }

  public BrandVoucherStatisticResponse getBrandVoucherStatisticResponse(String brandId, String startDate,
      String endDate) {
    List<Object[]> rawResults = brandRepository.getDailyVoucherStats(brandId, startDate, endDate);
    List<BrandStatisticResponse.BrandVoucherStatisticObject> results = rawResults.stream()
        .map(row -> new BrandStatisticResponse.BrandVoucherStatisticObject(
            (String) row[0],
            (Integer) row[1]))
        .collect(Collectors.toList());

    BrandStatisticResponse.BrandVoucherStatisticResponse response = new BrandStatisticResponse.BrandVoucherStatisticResponse();
    response.setData(results);
    return response;
  }
}
