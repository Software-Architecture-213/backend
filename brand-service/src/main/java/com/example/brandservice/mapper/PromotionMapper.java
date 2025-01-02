package com.example.brandservice.mapper;

import com.example.brandservice.dto.request.PromotionRequest;
import com.example.brandservice.dto.response.PromotionResponse;
import com.example.brandservice.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    // Mapping fields from PromotionRequest to Promotion
    @Mapping(target = "remainingBudget", ignore = true)
    @Mapping(target = "games", source = "games")
    Promotion promotionRequestToPromotion(PromotionRequest promotionRequest);

    // Mapping fields from Promotion to PromotionResponse
    @Mapping(target = "brandId", source = "brand.id")
    PromotionResponse promotionToPromotionResponse(Promotion promotion);
}
