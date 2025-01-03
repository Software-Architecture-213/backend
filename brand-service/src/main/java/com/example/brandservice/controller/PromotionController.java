package com.example.brandservice.controller;

import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.dto.request.PromotionRequest;
import com.example.brandservice.dto.response.BrandResponse;
import com.example.brandservice.dto.response.PromotionResponse;
import com.example.brandservice.service.BrandService;
import com.example.brandservice.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;
    private final BrandService brandService;

    // Create a new promotion
    @PostMapping
    public ResponseEntity<PromotionResponse> createPromotion(Authentication authentication, @RequestBody PromotionRequest promotionRequest) {
        String brandId = (String) authentication.getPrincipal();
        BrandResponse brandResponse = brandService.getBrandById(brandId);
        promotionRequest.setBrandId(brandResponse.getId());
        PromotionResponse createdPromotion = promotionService.createPromotion(promotionRequest);
        return new ResponseEntity<>(createdPromotion, HttpStatus.CREATED);
    }

    // Update an existing promotion
    @PutMapping("/{promotionId}")
    public ResponseEntity<PromotionResponse> updatePromotion(
            @PathVariable String promotionId,
            @RequestBody PromotionRequest promotionRequest) {
        PromotionResponse updatedPromotion = promotionService.updatePromotion(promotionId, promotionRequest);
        return new ResponseEntity<>(updatedPromotion, HttpStatus.OK);
    }

//    // Get a promotion by its ID
//    @GetMapping("/{promotionId}")
//    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable String promotionId) {
//        PromotionResponse promotionResponse = promotionService.getPromotionById(promotionId);
//        return new ResponseEntity<>(promotionResponse, HttpStatus.OK);
//    }

    // Get all promotions for a specific brand
    @GetMapping("/brand")
    public ResponseEntity<List<PromotionResponse>> getPromotionsByBrandId(@RequestParam("brandId") String brandId) {
        List<PromotionResponse> promotions = promotionService.getPromotionsByBrandId(brandId);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @PublicEndpoint
    @GetMapping
    public ResponseEntity<List<PromotionResponse>> getAllPromotions() {
        try {
            List<PromotionResponse> listPromotion = promotionService.getAllPromotions();
            return new ResponseEntity<>(listPromotion, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PublicEndpoint
    @GetMapping("/{promotionId}")
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable String promotionId) {
        PromotionResponse promotion = promotionService.getPromotionById(promotionId);
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }

    @PublicEndpoint
    @GetMapping("/my-promotions")
    public ResponseEntity<List<PromotionResponse>> getPromotions(Authentication authentication) {
        String brandId = (String) authentication.getPrincipal();
        BrandResponse brandResponse = brandService.getBrandById(brandId);
        List<PromotionResponse> promotions = promotionService.getPromotionsByBrandId(brandResponse.getId());
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }
}
