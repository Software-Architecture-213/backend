package com.example.brandservice.service;

import com.example.brandservice.dto.request.VoucherRequest;
import com.example.brandservice.dto.response.VoucherResponse;
import com.example.brandservice.mapper.VoucherMapper;
import com.example.brandservice.model.Promotion;
import com.example.brandservice.model.Voucher;
import com.example.brandservice.repository.PromotionRepository;
import com.example.brandservice.repository.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;
    private final PromotionRepository promotionRepository;

    public VoucherResponse createVoucher(VoucherRequest voucherRequest) {
        // Fetch the promotion associated with the voucher
        Promotion promotion = promotionRepository.findById(voucherRequest.getPromotionId())
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        // Map the voucher request to a voucher entity
        Voucher voucher = voucherMapper.toVoucher(voucherRequest);
        voucher.setPromotion(promotion);  // Set the promotion on the voucher
        voucher.setCreateAt(LocalDateTime.now());
        // If the promotion already has a list of vouchers, add the new voucher to it
        if (promotion.getVouchers() == null) {
            promotion.setVouchers(new ArrayList<>());  // Create a new list if none exists
        }

        // Add the new voucher to the promotion's vouchers list
        promotion.getVouchers().add(voucher);

        // Save the voucher to the repository
        voucherRepository.save(voucher);

        // Save the updated promotion (this will persist the voucher in the promotion's list)
        promotionRepository.save(promotion);

        // Return the response using the mapper
        return voucherMapper.toVoucherResponse(voucher);
    }

    public List<VoucherResponse> getVoucherByPromotionId(String promotionId) {
        promotionRepository.findById(promotionId).orElseThrow(
                () -> new RuntimeException("Promotion not found"));
        List<Voucher> vouchers = voucherRepository.findAllByPromotionId(promotionId);
        return vouchers.stream()
                .map(voucherMapper::toVoucherResponse)
                .toList();
    }

    public VoucherResponse getVoucherById(String voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(
                () -> new RuntimeException("Voucher not found")
        );

        return voucherMapper.toVoucherResponse(voucher);
    }

    public List<VoucherResponse> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(voucherMapper::toVoucherResponse)
                .toList();
    }

    public VoucherResponse updateVoucher(String id, VoucherRequest voucherRequest) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        if (optionalVoucher.isPresent()) {
            Voucher voucher = optionalVoucher.get();
            voucher = voucherMapper.toVoucher(voucherRequest);  // Update the voucher using the mapstruct mapper
            voucher.setId(id);  // Ensure the ID is set
            Voucher updatedVoucher = voucherRepository.save(voucher);
            return voucherMapper.toVoucherResponse(updatedVoucher); // Map to DTO
        } else {
            throw new RuntimeException("Voucher not found");
        }
    }
}
