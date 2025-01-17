package com.example.brandservice.controller;

import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.dto.request.BrandRequest;
import com.example.brandservice.dto.request.ChangeBrandStatusRequest;
import com.example.brandservice.dto.request.GetBrandsRequest;
import com.example.brandservice.dto.response.BrandResponse;
import com.example.brandservice.model.Branch;
import com.example.brandservice.service.BranchService;
import com.example.brandservice.service.BrandService;
import com.example.brandservice.service.PromotionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection")
@AllArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final PromotionService promotionService;
    private final BranchService branchService;


    // Create a new brand
    @PublicEndpoint
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

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{brandId}")
    public ResponseEntity<BrandResponse> updateBrandByAdmin(
            @PathVariable String brandId,
            @RequestBody @Valid BrandRequest brandRequest) {
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
    public ResponseEntity<List<BrandResponse>> getBrands(@ModelAttribute  GetBrandsRequest request) {
        List<BrandResponse> brandResponses = brandService.getAllBrands(request);
        List<String> brandIds = brandResponses.stream().map(BrandResponse::getId).toList();
        final Map<String, Branch> brandIdAndBranch = branchService.getMainBranches(brandIds);
        brandResponses.forEach(brandResponse -> {
            if (brandIdAndBranch.containsKey(brandResponse.getId())) {
                brandResponse.setHeadQuarter(brandIdAndBranch.get(brandResponse.getId()).getAddress());
            }
        });
        return new ResponseEntity<>(brandResponses, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{email}/status")
    public ResponseEntity<BrandResponse> changeStatus(@PathVariable String email, @RequestBody ChangeBrandStatusRequest request) {
        request.setEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(brandService.changeBrandStatus(request));
    }

}
