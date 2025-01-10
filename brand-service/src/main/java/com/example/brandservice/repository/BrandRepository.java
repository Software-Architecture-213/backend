package com.example.brandservice.repository;

import com.example.brandservice.dto.response.AdminStatisticResponse.AdminBrandStatisticObject;
import com.example.brandservice.model.Brand;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
  Optional<Brand> findByUsername(String username);

  Page<Brand> findAll(@NotNull Pageable pageable);

  @Query(value = "SELECT * FROM GetBrandCreationCount(CAST(:startDate AS DATE), CAST(:endDate AS DATE))", nativeQuery = true)
  List<Object[]> getBrandCreationCount(@Param("startDate") String startDate,
      @Param("endDate") String endDate);

  @Query(value = "SELECT * FROM getDailyVoucherStats(:brandId, CAST(:startDate AS DATE), CAST(:endDate AS DATE))", nativeQuery = true)
  List<Object[]> getDailyVoucherStats(@Param("brandId") String brandId,
      @Param("startDate") String startDate, @Param("endDate") String endDate);
}
