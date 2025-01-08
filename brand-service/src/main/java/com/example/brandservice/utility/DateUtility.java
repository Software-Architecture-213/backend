package com.example.brandservice.utility;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.Date;

@Component
public class DateUtility {

    public static Date convert(@NonNull final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneOffset.UTC).toInstant());
    }
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        // Convert Date to Instant
        Instant instant = date.toInstant();

        // Convert Instant to LocalDateTime in the system default time zone
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
