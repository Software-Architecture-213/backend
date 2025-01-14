package com.example.brandservice.mapper;

import com.example.brandservice.dto.request.VoucherRequest;
import com.example.brandservice.dto.response.VoucherResponse;
import com.example.brandservice.model.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
    VoucherMapper INSTANCE = Mappers.getMapper(VoucherMapper.class);
    @Mapping(source = "expiredAt", target = "expiredAt", qualifiedByName = "dateToLocalDateTime")
    Voucher toVoucher(VoucherRequest voucherRequest);

    @Mapping(source = "promotion.id", target = "promotionId")
    VoucherResponse toVoucherResponse(Voucher voucher);

    // Custom method to convert Date to LocalDateTime
    @Named("dateToLocalDateTime")
    default LocalDateTime dateToLocalDateTime(Date date) {
        if (date != null) {
            return Instant.ofEpochMilli(date.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }
        return null;
    }
}
