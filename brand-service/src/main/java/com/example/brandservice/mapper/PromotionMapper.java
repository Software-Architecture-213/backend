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
    PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

    // Mapping fields from PromotionRequest to Promotion
    @Mappings({
            @Mapping(target = "id", ignore = true), // Ignore ID as it may be auto-generated
            @Mapping(target = "brand", ignore = true), // Handle brand mapping in service if necessary
            @Mapping(target = "imageUrl", source = "imageUrl"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "numOfVouchers", source = "numOfVouchers"),
            @Mapping(target = "startDate", source = "startDate"),
            @Mapping(target = "endDate", source = "endDate"),
            @Mapping(target = "status", source = "status")
    })
    Promotion promotionRequestToPromotion(PromotionRequest promotionRequest);

    // Mapping fields from Promotion to PromotionResponse
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "brandId", source = "brand.id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "imageUrl", source = "imageUrl"),
            @Mapping(target = "numOfVouchers", source = "numOfVouchers"),
            @Mapping(target = "startDate", source = "startDate"),
            @Mapping(target = "endDate", source = "endDate"),
            @Mapping(target = "status", source = "status")
    })
    PromotionResponse promotionToPromotionResponse(Promotion promotion);
}