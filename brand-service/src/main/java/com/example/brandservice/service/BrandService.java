package com.example.brandservice.service;

import com.example.brandservice.dto.request.BrandRequest;
import com.example.brandservice.dto.response.BrandResponse;
import com.example.brandservice.mapper.BrandMapper;
import com.example.brandservice.model.Brand;
import com.example.brandservice.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Transactional
    public BrandResponse createBrand(BrandRequest brandRequest) {
        Brand brand = brandMapper.brandRequestToBrand(brandRequest);
        brand.setCreateAt(LocalDateTime.now());
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.brandToBrandResponse(savedBrand);
    }

    public BrandResponse getBrandById(String id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        return brandMapper.brandToBrandResponse(brand);
    }

    public BrandResponse updateBrand(String id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        brand.setDisplayName(brandRequest.getDisplayName());
        brand.setImageUrl(brandRequest.getImageUrl());
        brand.setField(brandRequest.getField());
        brand.setGps(brandRequest.getGps());
        brand.setStatus(brandRequest.getStatus());
        brand.setUpdateAt(LocalDateTime.now());
        Brand updatedBrand = brandRepository.save(brand);
        return brandMapper.brandToBrandResponse(updatedBrand);
    }

    public BrandResponse getUserInfoByEmail(String email) {
        return brandMapper.brandToBrandResponse(brandRepository.findByUsername(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found")
        ));
    }

    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(brandMapper::brandToBrandResponse)
                .collect(Collectors.toList());
    }

    public void deleteBrand(String id) {
        brandRepository.deleteById(id);
    }
}
