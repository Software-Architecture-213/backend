package com.example.brandservice.client;

import com.example.brandservice.dto.response.ValidatedTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class IdentityClient {

    @Value("${service.identity-url}")
    private String IDENTITY_URL;

    private final RestTemplate restTemplate;

    public ValidatedTokenResponse introspectToken(String token) {
        String url = UriComponentsBuilder.fromUriString(IDENTITY_URL)
                .path("/identity/auth/jwt-introspect")
                .queryParam("token", token)
                .toUriString();

        return restTemplate.getForObject(url, ValidatedTokenResponse.class);
    }
}
