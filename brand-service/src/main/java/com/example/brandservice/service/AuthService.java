package com.example.brandservice.service;

import com.example.brandservice.dto.request.BrandRequest;
import com.example.brandservice.dto.request.LoginRequest;
import com.example.brandservice.dto.response.RefreshTokenResponse;
import com.example.brandservice.dto.response.TokenResponse;
import com.example.brandservice.mapper.BrandMapper;
import com.example.brandservice.model.Brand;
import com.example.brandservice.repository.BrandRepository;
import com.example.brandservice.utility.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final JwtUtil jwtUtil;

    public void register(BrandRequest request) {
        Optional<Brand> existingBrand = brandRepository.findByEmail(request.getEmail());
        if (existingBrand.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        brandRepository.save(brandMapper.brandRequestToBrand(request));
    }

    public TokenResponse login(LoginRequest request) {
        Brand brand = brandRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!brand.getPassword().equals(request.getPassword())) { // Hash checking in production
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String accessToken = null;
        String refreshToken = null;
        try {
            accessToken = jwtUtil.generateToken(brand.getId(), "BRAND", brand.getEmail());
            refreshToken = jwtUtil.generateRefreshToken(brand.getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new TokenResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshAccessToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
        }

        String subject = jwtUtil.extractSubject(refreshToken);
        if (subject == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }

        Brand brand = brandRepository.findById(subject).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token")
        );
        // Generate a new access token
        String newAccessToken = jwtUtil.generateToken(brand.getId(), "BRAND", brand.getEmail());

        // Calculate the expiration time (example: 3600 seconds = 1 hour)
        String expiresIn = "3600";  // Adjust this based on your token expiration strategy

        // Return the response with the new access token and the same refresh token
        return RefreshTokenResponse.builder()
                .id_token(newAccessToken)
                .refresh_token(refreshToken)
                .expires_in(expiresIn)
                .build();
    }

}
