package com.example.brandservice.utility;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class DateUtility {

    public Date convert(@NonNull final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneOffset.UTC).toInstant());
    }

}
