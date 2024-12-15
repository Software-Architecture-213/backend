package com.example.brandservice.controller;

import com.example.brandservice.dto.request.BrandRequest;
import com.example.brandservice.dto.response.BrandResponse;
import com.example.brandservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collection")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    // Create a new brand
    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@RequestBody BrandRequest brandRequest) {
        BrandResponse createdBrand = brandService.createBrand(brandRequest);
        return new ResponseEntity<>(createdBrand, HttpStatus.CREATED);
    }

    // Update an existing brand
    @PutMapping("/{brandId}")
    public ResponseEntity<BrandResponse> updateBrand(
            @PathVariable String brandId,
            @RequestBody BrandRequest brandRequest) {
        BrandResponse updatedBrand = brandService.updateBrand(brandId, brandRequest);
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }

    // Get a brand by its ID
    @GetMapping("/{brandId}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable String brandId) {
        BrandResponse brandResponse = brandService.getBrandById(brandId);
        return new ResponseEntity<>(brandResponse, HttpStatus.OK);
    }

    // Get all brands
    @PreAuthorize("hasRole('BRAND')")
//    @PreAuthorize("hasAnyRole('BRAND', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        List<BrandResponse> brandResponses = brandService.getAllBrands();
        return new ResponseEntity<>(brandResponses, HttpStatus.OK);
    }
}
