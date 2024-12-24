package com.example.brandservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRequest {
    private String voucherCode;
    private String qrCode;
    private String imageUrl;
    private Double value;
    private String description;
    private Date expirationDate;
    private String status;
    private String promotionId;
}
