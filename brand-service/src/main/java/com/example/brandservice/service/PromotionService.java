package com.example.brandservice.service;

import com.example.brandservice.dto.request.PromotionRequest;
import com.example.brandservice.dto.response.PromotionResponse;
import com.example.brandservice.mapper.PromotionMapper;
import com.example.brandservice.model.Promotion;
import com.example.brandservice.model.Brand;
import com.example.brandservice.model.Voucher;
import com.example.brandservice.repository.PromotionRepository;
import com.example.brandservice.repository.BrandRepository;
import com.example.brandservice.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final BrandRepository brandRepository;
    private final PromotionMapper promotionMapper;
    private final VoucherRepository voucherRepository;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository, BrandRepository brandRepository, PromotionMapper promotionMapper, VoucherRepository voucherRepository) {
        this.promotionRepository = promotionRepository;
        this.brandRepository = brandRepository;
        this.promotionMapper = promotionMapper;
        this.voucherRepository = voucherRepository;
    }

    // Create a new promotion
    @Transactional
    public PromotionResponse createPromotion(PromotionRequest promotionRequest) {
        // Get the brand by ID
        Optional<Brand> brandOptional = brandRepository.findById(promotionRequest.getBrandId());
        if (brandOptional.isEmpty()) {
            throw new RuntimeException("Brand not found with id: " + promotionRequest.getBrandId());
        }
        Brand brand = brandOptional.get();

        // Map the PromotionRequest to a Promotion entity
        Promotion promotion = promotionMapper.promotionRequestToPromotion(promotionRequest);
        promotion.setBrand(brand); // Set the Brand in the Promotion entity
        promotion.setRemainingVouchers(promotion.getNumOfVouchers());
        // Save the promotion entity
        Promotion savedPromotion = promotionRepository.save(promotion);

        // Return the saved promotion as a response DTO
        return promotionMapper.promotionToPromotionResponse(savedPromotion);
    }

    // Update an existing promotion
    @Transactional
    public PromotionResponse updatePromotion(String promotionId, PromotionRequest promotionRequest) {
        // Find the promotion by ID
        Optional<Promotion> existingPromotion = promotionRepository.findById(promotionId);
        if (existingPromotion.isEmpty()) {
            throw new RuntimeException("Promotion not found with id: " + promotionId);
        }
        Promotion promotion = existingPromotion.get();

        // Get the brand by ID
        Optional<Brand> brandOptional = brandRepository.findById(promotionRequest.getBrandId());
        if (brandOptional.isEmpty()) {
            throw new RuntimeException("Brand not found with id: " + promotionRequest.getBrandId());
        }
        Brand brand = brandOptional.get();

        // Update the promotion details
        promotion.setBrand(brand);
        promotion.setImageUrl(promotionRequest.getImageUrl());
        promotion.setNumOfVouchers(promotionRequest.getNumOfVouchers());
        promotion.setName(promotionRequest.getName());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());
        promotion.setStatus(promotionRequest.getStatus());

        // Save the updated promotion entity
        Promotion updatedPromotion = promotionRepository.save(promotion);

        // Return the updated promotion as a response DTO
        return promotionMapper.promotionToPromotionResponse(updatedPromotion);
    }

    // Fetch a promotion by ID
    public PromotionResponse getPromotionById(String promotionId) {
        // Find the promotion by ID
        Optional<Promotion> promotion = promotionRepository.findById(promotionId);
        if (promotion.isPresent()) {
            return promotionMapper.promotionToPromotionResponse(promotion.get());
        } else {
            throw new RuntimeException("Promotion not found with id: " + promotionId);
        }
    }

    // Fetch all promotions for a specific brand
    public List<PromotionResponse> getPromotionsByBrandId(String brandId) {
        List<Promotion> promotions;
         if (brandId != null && !brandId.isBlank()) {
            promotions = promotionRepository.findByBrandId(brandId);
         }
         promotions = promotionRepository.findAll();
        // Manually map List<Promotion> to List<PromotionResponse>
        return promotions.stream()
                .map(promotionMapper::promotionToPromotionResponse)
                .toList();
    }
}
