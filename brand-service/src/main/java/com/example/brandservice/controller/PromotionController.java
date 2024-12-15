package com.example.brandservice.controller;

import com.example.brandservice.dto.request.PromotionRequest;
import com.example.brandservice.dto.response.PromotionResponse;
import com.example.brandservice.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    // Create a new promotion
    @PostMapping
    public ResponseEntity<PromotionResponse> createPromotion(@RequestBody PromotionRequest promotionRequest) {
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

    // Get a promotion by its ID
    @GetMapping("/{promotionId}")
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable String promotionId) {
        PromotionResponse promotionResponse = promotionService.getPromotionById(promotionId);
        return new ResponseEntity<>(promotionResponse, HttpStatus.OK);
    }

    // Get all promotions for a specific brand
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<PromotionResponse>> getPromotionsByBrandId(@PathVariable String brandId) {
        List<PromotionResponse> promotions = promotionService.getPromotionsByBrandId(brandId);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    // Get all promotions
    @GetMapping
    public ResponseEntity<List<PromotionResponse>> getAllPromotions() {
        List<PromotionResponse> promotions = promotionService.getAllPromotions();
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }
}
