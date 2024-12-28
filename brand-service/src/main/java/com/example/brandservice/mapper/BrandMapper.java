package com.example.brandservice.mapper;

import com.example.brandservice.dto.request.BrandRequest;
import com.example.brandservice.dto.response.BrandResponse;
import com.example.brandservice.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    // Mapping fields from BrandRequest to Brand
    Brand brandRequestToBrand(BrandRequest brandRequest);

    // Mapping fields from Brand to BrandResponse
    BrandResponse brandToBrandResponse(Brand brand);
}
