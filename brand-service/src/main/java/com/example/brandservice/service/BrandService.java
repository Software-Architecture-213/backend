package com.example.brandservice.service;

import com.example.brandservice.dto.request.BrandRequest;
import com.example.brandservice.dto.response.BrandResponse;
import com.example.brandservice.mapper.BrandMapper;
import com.example.brandservice.model.Brand;
import com.example.brandservice.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    @Autowired
    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public BrandResponse createBrand(BrandRequest brandRequest) {
        Brand brand = brandMapper.brandRequestToBrand(brandRequest);
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
        brand.setName(brandRequest.getName());
        brand.setField(brandRequest.getField());
        brand.setAddress(brandRequest.getAddress());
        brand.setGps(brandRequest.getGps());
        brand.setStatus(brandRequest.getStatus());
        Brand updatedBrand = brandRepository.save(brand);
        return brandMapper.brandToBrandResponse(updatedBrand);
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
