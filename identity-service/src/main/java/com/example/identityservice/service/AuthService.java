package com.example.identityservice.service;

import com.example.identityservice.client.FirebaseAuthClient;
import com.example.identityservice.dto.request.auth.UserCreationRequest;
import com.example.identityservice.dto.request.auth.UserLoginRequest;
import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.response.auth.*;
import com.example.identityservice.exception.AccountAlreadyExistsException;
import com.example.identityservice.exception.AppException;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final FirebaseAuth firebaseAuth;
    private final FirebaseAuthClient firebaseAuthClient;
    private final Firestore firestore;

    @SneakyThrows
    public void create(@NonNull final UserCreationRequest userCreationRequest) {
        log.info("Creating user with email: {}", userCreationRequest.getEmail());
        final var request = new UserRecord.CreateRequest()
                .setEmail(userCreationRequest.getEmail())
                .setPassword(userCreationRequest.getPassword())
                .setEmailVerified(Boolean.TRUE)
                .setDisplayName(userCreationRequest.getDisplayName())
                .setPhoneNumber(userCreationRequest.getPhoneNumber())
//                .setDisabled(true) /* set true for OTP or account activation for admin implementation later */
                .setDisabled(false);
        /* photoUrl will be set when update profile */

        try {
            UserRecord userRecord = firebaseAuth.createUser(request);
            log.info("User successfully created: {}", userCreationRequest.getEmail());
            Map<String, Object> claims = Map.of("role", userCreationRequest.getRole().name());
            firebaseAuth.setCustomUserClaims(userRecord.getUid(), claims);
            log.info("Custom claims set for user ID {}: {}", userRecord.getUid(), claims);
            DocumentReference userDoc = firestore.collection("users").document(userRecord.getUid());
            Map<String, Object> additionalFields = new HashMap<>();
            additionalFields.put("dateOfBirth", userCreationRequest.getDateOfBirth());
            additionalFields.put("gender", userCreationRequest.getGender());
            userDoc.set(additionalFields).get();
        } catch (final FirebaseAuthException exception) {
            if (exception.getMessage().contains("EMAIL_EXISTS")) {
                throw new AccountAlreadyExistsException("Account with provided email already exists");
            }
            if (exception.getMessage().contains("PHONE_NUMBER_EXISTS")) {
                throw new AccountAlreadyExistsException("Account with provided phone number already exists");
            }
            throw new RuntimeException("Error creating user: " + exception.getMessage(), exception);
        }
    }

    public TokenSuccessResponse login(@NonNull final UserLoginRequest userLoginRequest) {
            return firebaseAuthClient.login(userLoginRequest);

    }

    public FirebaseGoogleSignInResponse loginWithGoogle(@NonNull final String idToken) throws FirebaseAuthException {
        FirebaseToken firebaseToken = firebaseAuthClient.verifyToken(idToken);

        return FirebaseGoogleSignInResponse.builder()
                .uid(firebaseToken.getUid())
                .email(firebaseToken.getEmail())
                .build();
    }

    public RefreshTokenSuccessResponse refreshAccessToken(@NonNull final String refreshToken) {
        return firebaseAuthClient.refreshAccessToken(refreshToken);
    }


    public ValidatedTokenResponse validateToken(@NonNull final String token) {
        try {
            FirebaseToken decodeToken = firebaseAuth.verifyIdToken(token);
            return ValidatedTokenResponse.builder()
                    .userId(decodeToken.getClaims().get("user_id").toString())
                    .role(decodeToken.getClaims().get("role").toString())
                    .isValidated(true)
                    .message("Token validation successful.")
                    .build();
        } catch (FirebaseAuthException e) {
            return ValidatedTokenResponse.builder()
                    .isValidated(false)
                    .message("Invalid token: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return ValidatedTokenResponse.builder()
                    .isValidated(false)
                    .message("An unexpected error occurred: " + e.getMessage())
                    .build();
        }
    }
}