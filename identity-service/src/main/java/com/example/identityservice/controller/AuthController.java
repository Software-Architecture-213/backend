package com.example.identityservice.controller;

import com.example.identityservice.configuration.PublicEndpoint;
import com.example.identityservice.dto.request.auth.UserCreationRequest;
import com.example.identityservice.dto.request.auth.UserLoginRequest;
import com.example.identityservice.dto.response.auth.*;
import com.example.identityservice.service.AuthService;
import com.example.identityservice.service.OTPService;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final OTPService otpService;

    @PublicEndpoint
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody final UserCreationRequest userCreationRequest) {
        authService.create(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PublicEndpoint
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenSuccessResponse> login(@Valid @RequestBody final UserLoginRequest userLoginRequest, HttpServletResponse response) {
        final var tokenResponse = authService.login(userLoginRequest);

        String refreshToken = tokenResponse.getRefreshToken();

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // Use secure flag in production
        refreshTokenCookie.setPath("/"); // Make the cookie available site-wide
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 1 week expiration
        response.addCookie(refreshTokenCookie);


        return ResponseEntity.ok(tokenResponse);
    }

    @PublicEndpoint
    @PostMapping(value = "/login/google")
    public ResponseEntity<FirebaseGoogleSignInResponse> loginWithGoogle(@RequestBody final Map<String, String> body) throws FirebaseAuthException {
        String idToken = body.get("idToken");
        FirebaseGoogleSignInResponse response = authService.loginWithGoogle(idToken);
        return ResponseEntity.ok(response);
    }


    @PublicEndpoint
    @PostMapping(value = "/refresh-token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenSuccessResponse> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("token");
        RefreshTokenSuccessResponse refreshTokenResponse = authService.refreshAccessToken(refreshToken);
        TokenSuccessResponse response = TokenSuccessResponse.builder()
                .accessToken(refreshTokenResponse.getId_token())
                .refreshToken(refreshTokenResponse.getRefresh_token())
                .build();
        return ResponseEntity.ok(response);
    }

    @PublicEndpoint
    @PostMapping("/jwt-introspect")
    public ResponseEntity<ValidatedTokenResponse> validateToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body(ValidatedTokenResponse.builder()
            .isValidated(false)
            .message("Invalid token.")
            .build());
        }
        ValidatedTokenResponse response = authService.validateToken(token);
        return ResponseEntity.ok(response);
    }

    @PublicEndpoint
    @PostMapping("/otp/generate")
    public ResponseEntity<String> generateOTP(@RequestParam String email) {
        otpService.generateAndSendOTP(email);
        return ResponseEntity.ok("OTP sent to " + email);
    }

    @PublicEndpoint
    @PostMapping("/otp/validate")
    public ResponseEntity<String> validateOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.validateOTP(email, otp);
        return isValid ? ResponseEntity.ok("OTP is valid!") : ResponseEntity.badRequest().body("Invalid or expired OTP.");
    }
}
