package com.example.brandservice.service;

import com.example.brandservice.configuration.PublicEndpoint;
import com.example.brandservice.dto.request.BrandRequest;
import com.example.brandservice.dto.request.LoginRequest;
import com.example.brandservice.dto.response.TokenResponse;
import com.example.brandservice.mapper.BrandMapper;
import com.example.brandservice.model.Brand;
import com.example.brandservice.repository.BrandRepository;
import com.example.brandservice.utility.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.io.Console;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final JwtUtil jwtUtil;

    @PublicEndpoint
    public void register(BrandRequest request) {
        Optional<Brand> existingBrand = brandRepository.findByEmail(request.getEmail());
        if (existingBrand.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        brandRepository.save(brandMapper.brandRequestToBrand(request));
    }

    @PublicEndpoint
    public TokenResponse login(LoginRequest request) {
        Brand brand = brandRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!brand.getPassword().equals(request.getPassword())) { // Hash checking in production
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String accessToken = null;
        String refreshToken = null;
        try {
            accessToken = jwtUtil.generateToken(brand.getEmail());
            refreshToken = jwtUtil.generateRefreshToken(brand.getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new TokenResponse(accessToken, refreshToken);
    }
}