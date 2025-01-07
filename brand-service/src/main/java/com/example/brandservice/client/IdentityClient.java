package com.example.brandservice.client;

import com.example.brandservice.dto.response.ValidatedTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IdentityClient {

    @Value("${service.identity-url}")
    private String IDENTITY_URL;

    private final RestTemplate restTemplate;

    public ValidatedTokenResponse introspectToken(String token) {
        String url = UriComponentsBuilder.fromUriString(IDENTITY_URL)
                .path("/identity/auth/jwt-introspect")
                .toUriString();

        // Create the request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("token", token);

        // Send the POST request with the request body
        return restTemplate.postForObject(url, requestBody, ValidatedTokenResponse.class);
    }
}
