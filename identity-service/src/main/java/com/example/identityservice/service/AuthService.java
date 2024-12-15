package com.example.identityservice.service;

import com.example.identityservice.client.FirebaseAuthClient;
import com.example.identityservice.dto.request.auth.UserCreationRequest;
import com.example.identityservice.dto.request.auth.UserLoginRequest;
import com.example.identityservice.dto.response.auth.*;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final FirebaseAuthClient firebaseAuthClient;

    @SneakyThrows
    public void create(@NonNull final UserCreationRequest userCreationRequest) {
       firebaseAuthClient.create(userCreationRequest);
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
        return  firebaseAuthClient.verifyIdToken(token);
    }
}