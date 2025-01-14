package com.example.brandservice.dto.response;

import com.example.brandservice.model.Voucher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {
    String id;
    String code;
    Voucher.VoucherType type;
    String imageUrl;
    Voucher.VoucherValue valueType;
    double value;
    String description;
    Date expiredAt;
    Voucher.VoucherStatus status;
    int maxCounts;
    int createCounts;
    Date createAt;
    Date updateAt;
    String promotionId;
}