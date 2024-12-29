package com.example.brandservice.service;

import com.example.brandservice.dto.request.PromotionRequest;
import com.example.brandservice.dto.response.PromotionResponse;
import com.example.brandservice.exception.AppException;
import com.example.brandservice.exception.ErrorCode;
import com.example.brandservice.mapper.PromotionMapper;
import com.example.brandservice.model.Promotion;
import com.example.brandservice.model.Brand;
import com.example.brandservice.repository.PromotionRepository;
import com.example.brandservice.repository.BrandRepository;
import com.example.brandservice.repository.VoucherRepository;
import com.example.brandservice.utility.CloudinaryUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final BrandRepository brandRepository;
    private final PromotionMapper promotionMapper;
    private final CloudinaryService cloudinaryService;


    // Create a new promotion
    public PromotionResponse createPromotion(PromotionRequest promotionRequest) {
        // Get the brand by ID
        Brand brand = brandRepository.findById(promotionRequest.getBrandId()).orElseThrow(
                () -> new RuntimeException("Brand not found")
        );

        // Map the PromotionRequest to a Promotion entity
        Promotion promotion = promotionMapper.promotionRequestToPromotion(promotionRequest);
        promotion.setBrand(brand); // Set the Brand in the Promotion entity
        promotion.setCreateAt(LocalDateTime.now());
        promotion.setRemainingBudget(promotion.getBudget());
        // Save the promotion entity
        Promotion savedPromotion = promotionRepository.save(promotion);

        // Return the saved promotion as a response DTO
        return promotionMapper.promotionToPromotionResponse(savedPromotion);
    }

    // Update an existing promotion
    public PromotionResponse updatePromotion(String promotionId, PromotionRequest promotionRequest) {
        // Find the promotion by ID
        Promotion promotion = promotionRepository.findById(promotionId).orElseThrow(
                () -> new RuntimeException("Promotion not found")
        );

        // Get the brand by ID
        Brand brand = brandRepository.findById(promotionRequest.getBrandId()).orElseThrow(
                () -> new RuntimeException("Brand not found")
        );

        // Update the promotion details
        promotion.setName(promotionRequest.getName());
        promotion.setDescription(promotionRequest.getDescription());
        promotion.setImageUrl(promotionRequest.getImageUrl());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());
        promotion.setBrand(brand);
        promotion.setBudget(promotionRequest.getBudget());
        promotion.setRemainingBudget(promotionRequest.getBudget());
        promotion.setStatus(promotionRequest.getStatus());
        promotion.setUpdateAt(LocalDateTime.now());
        // Save the updated promotion entity
        Promotion updatedPromotion = promotionRepository.save(promotion);

        // Return the updated promotion as a response DTO
        return promotionMapper.promotionToPromotionResponse(updatedPromotion);
    }

    public PromotionResponse uploadPhoto(String promotionId, MultipartFile file) {
        Promotion promotion = promotionRepository.findById(promotionId).orElseThrow(
                () -> new RuntimeException("Promotion not found")
        );

        String oldPhotoUrl = promotion.getImageUrl();
        String oldPublicId = CloudinaryUtil.extractPublicIdFromUrl(oldPhotoUrl);
        if (oldPublicId != null){
            cloudinaryService.deleteImage(oldPublicId);
        }
        String newPhotoUrl = cloudinaryService.uploadImage(file);
        if (newPhotoUrl == null){
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        promotion.setImageUrl(newPhotoUrl);
        return promotionMapper.promotionToPromotionResponse(promotionRepository.save(promotion));
    }

    // Fetch a promotion by ID
    public PromotionResponse getPromotionById(String promotionId) {
        // Find the promotion by ID
        Promotion promotion = promotionRepository.findById(promotionId).orElseThrow(
                () -> new RuntimeException("Promotion not found")
        );
        return promotionMapper.promotionToPromotionResponse(promotion);
    }

    // Fetch all promotions for a specific brand
    public List<PromotionResponse> getPromotionsByBrandId(String brandId) {
        List<Promotion> promotions;
         if (brandId != null && !brandId.isBlank()) {
            promotions = promotionRepository.findByBrandId(brandId);
        // Manually map List<Promotion> to List<PromotionResponse>
            return promotions.stream()
                .map(promotionMapper::promotionToPromotionResponse)
                .toList();
         }
         return null;
    }

    public List<PromotionResponse> getAllPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream()
                .map(promotionMapper::promotionToPromotionResponse)
                .toList();
    }
}
