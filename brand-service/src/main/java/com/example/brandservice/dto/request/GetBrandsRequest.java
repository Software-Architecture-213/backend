package com.example.brandservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetBrandsRequest {
    int page = 0;
    int pageSize = 8;
}
