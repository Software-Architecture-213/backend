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
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    // Mapping fields from BrandRequest to Brand
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "field", source = "field"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "gpsLat", source = "gpsLat"),
            @Mapping(target = "gpsLong", source = "gpsLong"),
            @Mapping(target = "status", source = "status")
    })
    Brand brandRequestToBrand(BrandRequest brandRequest);

    // Mapping fields from Brand to BrandResponse
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "field", source = "field"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "gpsLat", source = "gpsLat"),
            @Mapping(target = "gpsLong", source = "gpsLong"),
            @Mapping(target = "status", source = "status")
    })
    BrandResponse brandToBrandResponse(Brand brand);
}
