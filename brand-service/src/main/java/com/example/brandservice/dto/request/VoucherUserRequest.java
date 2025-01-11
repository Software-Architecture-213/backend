package com.example.brandservice.dto.request;

import com.example.brandservice.model.VoucherUser;
import lombok.Data;

import java.util.Date;

@Data
public class VoucherUserRequest {
    String voucherId;
    String userId;
    String qrCode;
    VoucherUser.VoucherUserStatus status;
    Date redeemedAt;
}
