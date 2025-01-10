package com.example.brandservice.service;

import com.example.brandservice.client.GameClient;
import com.example.brandservice.dto.request.ConversionRuleRequest;
import com.example.brandservice.dto.request.PromotionRequest;
import com.example.brandservice.dto.response.PromotionResponse;
import com.example.brandservice.exception.AppException;
import com.example.brandservice.exception.ErrorCode;
import com.example.brandservice.mapper.PromotionMapper;
import com.example.brandservice.model.*;
import com.example.brandservice.model.composite.ConversionRuleItemKey;
import com.example.brandservice.repository.*;
import com.example.brandservice.utility.CloudinaryUtil;
import com.example.brandservice.utility.ParseUUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class PromotionService {
    private final GameClient gameClient;
    private final PromotionRepository promotionRepository;
    private final BrandRepository brandRepository;
    private final PromotionMapper promotionMapper;
    private final CloudinaryService cloudinaryService;
    private final FavouritePromotionsRepository favouritePromotionsRepository;
    private final ConversionRuleRepository conversionRuleRepository;
    private final VoucherRepository voucherRepository;
    private final ConversionRuleItemRepository conversionRuleItemRepository;
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
        gameClient.createPromotions(savedPromotion);
        // Return the saved promotion as a response DTO
        return promotionMapper.promotionToPromotionResponse(savedPromotion);
    }

    // Update an existing promotion
    public PromotionResponse updatePromotion(String promotionId, PromotionRequest promotionRequest) {
        // Find the promotion by ID
        Promotion promotion = promotionRepository.findById(promotionId).orElseThrow(
                () -> new RuntimeException("Promotion not found")
        );

        // Update the promotion details
        promotion.setName(promotionRequest.getName());
        promotion.setDescription(promotionRequest.getDescription());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());
        if (promotionRequest.getBudget() > promotion.getBudget()){
            promotion.setRemainingBudget(promotionRequest.getBudget() - promotion.getBudget() + promotion.getRemainingBudget());
        }
        promotion.setBudget(promotionRequest.getBudget());
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

    @Transactional
    public FavouritePromotions addToFavourites(String promotionId, String userId) {
        FavouritePromotions favouritePromotions = favouritePromotionsRepository.findByUserId(ParseUUID.normalizeUID(userId))
                .orElseGet(() -> {
                    FavouritePromotions newFavourite = new FavouritePromotions();
                    newFavourite.setUserId(ParseUUID.normalizeUID(userId));
                    return newFavourite;
                });

        // Lấy danh sách promotions
        List<Promotion> promotions = favouritePromotions.getPromotions();

        // Kiểm tra nếu promotionId đã tồn tại, nếu không thì thêm vào
        if (promotions == null) {
            promotions = new ArrayList<>();
        }
        boolean exists = promotions.stream()
                .anyMatch(promotion -> promotion.getId().equals(promotionId));

        if (!exists) {
            Promotion promotion = promotionRepository.findById(promotionId)
                    .orElseThrow(() -> new RuntimeException("Promotion not found"));
            promotions.add(promotion);
        }
        favouritePromotions.setPromotions(promotions);

        // Lưu lại FavouritePromotions
        return favouritePromotionsRepository.save(favouritePromotions);
    }

    public List<ConversionRule> getAllConversionRules() {
        return conversionRuleRepository.findAll();
    }

    public ConversionRule createConversionRule(ConversionRuleRequest request) {
        // Create and save the ConversionRule
        Voucher voucher = voucherRepository.findById(request.getVoucherId())
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        Promotion promotion = promotionRepository.findById(request.getPromotionId())
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        ConversionRule conversionRule = new ConversionRule();
        conversionRule.setVoucher(voucher);
        conversionRule.setPromotion(promotion);
        conversionRule.setCreateAt(LocalDateTime.now());
        ConversionRule savedRule = conversionRuleRepository.save(conversionRule);

        // Create and save ConversionRuleItems
        List<ConversionRuleItem> items = new ArrayList<>();
        for (ConversionRuleRequest.ConversionRuleItemRequest itemRequest : request.getItems()) {
            ConversionRuleItem item = new ConversionRuleItem();
            item.setId(new ConversionRuleItemKey(savedRule.getId(), itemRequest.getItemId()));
            item.setQuantity(itemRequest.getQuantity());
            items.add(item);
        }

        conversionRuleItemRepository.saveAll(items);
        savedRule.setItems(items);
        conversionRuleRepository.save(savedRule);
        return savedRule;
    }

    public ConversionRule getConversionRuleByPromotionId(String promotionId) {

        return conversionRuleRepository.findConversionRuleByPromotionId(promotionId).orElseThrow(
                () -> new RuntimeException("ConversionRule not found")
        );
    }
}
