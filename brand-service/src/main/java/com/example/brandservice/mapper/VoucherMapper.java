package com.example.brandservice.mapper;

import com.example.brandservice.dto.request.VoucherRequest;
import com.example.brandservice.dto.response.VoucherResponse;
import com.example.brandservice.model.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    @Mapping(target = "promotion", ignore = true)
    Voucher toVoucher(VoucherRequest voucherRequest);

    @Mapping(source = "promotion.id", target = "promotionId")
    VoucherResponse toVoucherResponse(Voucher voucher);
}
