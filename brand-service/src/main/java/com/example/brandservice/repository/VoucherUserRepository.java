package com.example.brandservice.repository;

import com.example.brandservice.model.VoucherUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherUserRepository extends JpaRepository<VoucherUser, String> {
    List<VoucherUser> findByUserId(String userId);
}
