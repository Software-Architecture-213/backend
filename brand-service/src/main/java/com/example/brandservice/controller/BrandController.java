package com.example.brandservice.controller;

import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.dto.request.BrandRequest;
import com.example.brandservice.dto.response.BrandResponse;
import com.example.brandservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PutMapping()
    public ResponseEntity<BrandResponse> updateBrand(
            Authentication authentication,
            @RequestBody BrandRequest brandRequest) {
        String brandId = (String) authentication.getPrincipal();
        BrandResponse updatedBrand = brandService.updateBrand(brandId, brandRequest);
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<BrandResponse> updatePhoto(Authentication authentication,
                                                        @RequestParam("file") MultipartFile file) {
        String brandId = (String) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(brandService.updatePhoto(brandId, file));
    }

    // Get a brand by its ID
    @PublicEndpoint
    @GetMapping("/{brandId}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable String brandId) {
        BrandResponse brandResponse = brandService.getBrandById(brandId);
        return new ResponseEntity<>(brandResponse, HttpStatus.OK);
    }

    // Get all brands
    @PublicEndpoint
    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        List<BrandResponse> brandResponses = brandService.getAllBrands();
        return new ResponseEntity<>(brandResponses, HttpStatus.OK);
    }
}
