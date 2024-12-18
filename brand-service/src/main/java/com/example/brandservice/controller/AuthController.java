package com.example.brandservice.controller;

import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.dto.request.BrandRequest;
import com.example.brandservice.dto.request.LoginRequest;
import com.example.brandservice.dto.response.BrandResponse;
import com.example.brandservice.dto.response.RefreshTokenResponse;
import com.example.brandservice.dto.response.TokenResponse;
import com.example.brandservice.service.AuthService;
import com.example.brandservice.service.BrandService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final BrandService brandService;

    @PublicEndpoint
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody final BrandRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PublicEndpoint
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody final LoginRequest request) {
        final var tokenResponse = authService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PublicEndpoint
    @PostMapping(value = "/refresh-token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("token");
        RefreshTokenResponse refreshTokenResponse = authService.refreshAccessToken(refreshToken);
        TokenResponse response = TokenResponse.builder()
                .accessToken(refreshTokenResponse.getId_token())
                .refreshToken(refreshTokenResponse.getRefresh_token())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<BrandResponse> getMyProfile(Authentication authentication) {
        // Extract the email (sub) from the Authentication object
        String email = (String) authentication.getPrincipal();

        // Call the service to fetch user info using the email
        BrandResponse response = brandService.getUserInfoByEmail(email);

        return ResponseEntity.ok(response);
    }
}
