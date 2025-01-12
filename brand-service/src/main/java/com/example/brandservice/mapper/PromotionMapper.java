package com.example.brandservice.mapper;

import com.example.brandservice.dto.request.PromotionRequest;
import com.example.brandservice.dto.response.PromotionResponse;
import com.example.brandservice.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    // Mapping fields from PromotionRequest to Promotion
    @Mapping(target = "remainingBudget", ignore = true)
    @Mapping(target = "games", source = "games")
    Promotion promotionRequestToPromotion(PromotionRequest promotionRequest);

    // Mapping fields from Promotion to PromotionResponse
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.displayName")
//    @Mapping(target = "description", source = "description")
    PromotionResponse promotionToPromotionResponse(Promotion promotion);
}
