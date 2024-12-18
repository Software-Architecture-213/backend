package com.example.brandservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshTokenResponse {
    private String id_token;
    private String refresh_token;
    private String expires_in;
}
