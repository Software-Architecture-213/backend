package com.example.brandservice.repository;

import com.example.brandservice.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
    Optional<Voucher> findByVoucherCode(String voucherCode);
    List<Voucher> findAllByPromotionId(String promotionId);
}
