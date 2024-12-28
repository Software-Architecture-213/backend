package com.example.brandservice.dto.request;

import com.example.brandservice.model.Voucher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.json.JsonValue;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherRequest {
    String code;
    Voucher.VoucherType type;
    String imageUrl;
    Voucher.VoucherValue valueType;
    double value;
    String description;
    Date expiredAt;
    Voucher.VoucherStatus status;
    String promotionId;
    int maxCounts;
    int createCounts;
}
