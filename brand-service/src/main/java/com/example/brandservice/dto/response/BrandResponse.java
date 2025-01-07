package com.example.brandservice.dto.response;

import com.example.brandservice.model.Brand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandResponse {
    String id;
    String displayName;
    String imageUrl;
    String username;
    String field;
    Brand.GPS gps;
    Brand.BrandStatus status;
    Date createAt;
    Date updateAt;

    public String getCreateAt() {
        if (createAt == null || createAt.toString().contains("1970")) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(createAt);
    }
}
