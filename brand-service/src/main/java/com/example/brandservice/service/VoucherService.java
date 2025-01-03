package com.example.brandservice.service;

import com.example.brandservice.dto.request.VoucherRequest;
import com.example.brandservice.dto.response.VoucherResponse;
import com.example.brandservice.exception.AppException;
import com.example.brandservice.exception.ErrorCode;
import com.example.brandservice.mapper.VoucherMapper;
import com.example.brandservice.model.Promotion;
import com.example.brandservice.model.Voucher;
import com.example.brandservice.repository.PromotionRepository;
import com.example.brandservice.repository.VoucherRepository;
import com.example.brandservice.utility.CloudinaryUtil;
import com.example.brandservice.utility.DateUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final CloudinaryService cloudinaryService;
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
        Voucher voucher = voucherRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Voucher not found")
        );

        voucher.setCode(voucherRequest.getCode());
        voucher.setType(voucherRequest.getType());
        voucher.setValueType(voucherRequest.getValueType());
        voucher.setValue(voucherRequest.getValue());
        voucher.setDescription(voucherRequest.getDescription());
        voucher.setUpdateAt(LocalDateTime.now());
        voucher.setStatus(voucherRequest.getStatus());
        voucher.setCreateCounts(voucherRequest.getCreateCounts());
        voucher.setMaxCounts(voucherRequest.getMaxCounts());
        voucher.setExpiredAt(DateUtility.convertDateToLocalDateTime(voucherRequest.getExpiredAt()));
        return voucherMapper.toVoucherResponse(voucherRepository.save(voucher));
    }

    public VoucherResponse uploadPhoto(String voucherId, MultipartFile file) {
        Voucher voucher= voucherRepository.findById(voucherId).orElseThrow(
                () -> new RuntimeException("Voucher not found")
        );

        String oldPhotoUrl = voucher.getImageUrl();
        String oldPublicId = CloudinaryUtil.extractPublicIdFromUrl(oldPhotoUrl);
        if (oldPublicId != null){
            cloudinaryService.deleteImage(oldPublicId);
        }
        String newPhotoUrl = cloudinaryService.uploadImage(file);
        if (newPhotoUrl == null){
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        voucher.setImageUrl(newPhotoUrl);
        return voucherMapper.toVoucherResponse(voucherRepository.save(voucher));
    }
}
