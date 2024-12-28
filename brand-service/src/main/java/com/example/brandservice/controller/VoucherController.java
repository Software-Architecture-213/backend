package com.example.brandservice.controller;

import com.example.brandservice.dto.request.VoucherRequest;
import com.example.brandservice.dto.response.VoucherResponse;
import com.example.brandservice.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // Create a new voucher
    @PostMapping
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherRequest voucherRequest) {
        try {
            // Create a voucher using the service layer
            VoucherResponse voucherResponse = voucherService.createVoucher(voucherRequest);
            return new ResponseEntity<>(voucherResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle any exceptions that occur during the creation
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{promotionId}")
    public ResponseEntity<List<VoucherResponse>> getVoucherByPromotionId(@PathVariable String promotionId) {
        try {
            // Create a voucher using the service layer
            List<VoucherResponse> voucherResponse = voucherService.getVoucherByPromotionId(promotionId);
            return new ResponseEntity<>(voucherResponse, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any exceptions that occur during the creation
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing voucher
    @PutMapping("/{id}")
    public ResponseEntity<VoucherResponse> updateVoucher(@PathVariable String id, @RequestBody VoucherRequest voucherRequest) {
        try {
            // Update a voucher using the service layer
            VoucherResponse voucherResponse = voucherService.updateVoucher(id, voucherRequest);
            return new ResponseEntity<>(voucherResponse, HttpStatus.OK);
        } catch (Exception e) {
            // Handle cases where the voucher might not be found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}