package com.example.brandservice.controller;

import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.dto.request.ConversionRuleRequest;
import com.example.brandservice.dto.request.PromotionRequest;
import com.example.brandservice.dto.response.BrandResponse;
import com.example.brandservice.dto.response.PromotionResponse;
import com.example.brandservice.dto.response.VoucherResponse;
import com.example.brandservice.model.ConversionRule;
import com.example.brandservice.model.FavouritePromotions;
import com.example.brandservice.model.Voucher;
import com.example.brandservice.service.BrandService;
import com.example.brandservice.service.PromotionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/promotions")
@AllArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;
    private final BrandService brandService;

    // Create a new promotion
    @PostMapping
    public ResponseEntity<PromotionResponse> createPromotion(Authentication authentication,
            @RequestBody PromotionRequest promotionRequest) {
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

    // Get all promotions for a specific brand
    @GetMapping("/brand")
    public ResponseEntity<List<PromotionResponse>> getPromotionsByBrandId(@RequestParam("brandId") String brandId) {
        List<PromotionResponse> promotions = promotionService.getPromotionsByBrandId(brandId);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @PostMapping("/upload-image/{promotionId}")
    public ResponseEntity<PromotionResponse> updatePhoto(
            @PathVariable String promotionId,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(promotionService.uploadPhoto(promotionId, file));
    }

    @PublicEndpoint
    @GetMapping("/{promotionId}")
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable String promotionId) {
        PromotionResponse promotion = promotionService.getPromotionById(promotionId);
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }

    @GetMapping("/me/get")
    public ResponseEntity<List<PromotionResponse>> getPromotions(Authentication authentication) {
        String brandId = (String) authentication.getPrincipal();
        BrandResponse brandResponse = brandService.getBrandById(brandId);
        List<PromotionResponse> promotions = promotionService.getPromotionsByBrandId(brandResponse.getId());
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
    @GetMapping("/{promotionId}/random/voucher")
    public ResponseEntity<VoucherResponse> randomVoucher(@PathVariable String promotionId) {
        try {
            VoucherResponse response = promotionService.getRandomVoucherByPromotionId(promotionId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/favourite/{promotionId}")
    public ResponseEntity<FavouritePromotions> favourite(@PathVariable String promotionId,
            Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        FavouritePromotions response = promotionService.addToFavourites(promotionId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PublicEndpoint
    @PostMapping("/promotion/conversions-rule")
    public ResponseEntity<ConversionRule> createPromotionConversionRule(
            @RequestBody ConversionRuleRequest conversionRuleRequest) {
        ConversionRule response = promotionService.createConversionRule(conversionRuleRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PublicEndpoint
    @GetMapping("/promotion/conversions-rule")
    public ResponseEntity<ConversionRule> getPromotionConversionRuleById(
            @RequestParam("promotionId") String promotionId) {
        ConversionRule response = promotionService.getConversionRuleByPromotionId(promotionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
