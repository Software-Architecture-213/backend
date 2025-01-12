package com.example.brandservice.repository;

import com.example.brandservice.model.Voucher;
import com.example.brandservice.model.VoucherUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherUserRepository extends JpaRepository<VoucherUser, String> {
    List<VoucherUser> findByUserId(String userId);
    Optional<VoucherUser> findByUserIdAndVoucher(String userId, Voucher voucher);
}
