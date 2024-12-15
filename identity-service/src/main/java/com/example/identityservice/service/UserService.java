package com.example.identityservice.service;

import com.example.identityservice.client.FirebaseUserClient;
import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.response.auth.UserInfoResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final FirebaseUserClient firebaseUserClient;

    public UserInfoResponse getUserInfo(String userId, String email) {
       return firebaseUserClient.getUserInfo(userId, email);
    }

    public UserInfoResponse updateByEmail(@NonNull String email, @NonNull UserUpdateRequest userUpdateRequest) {
        return firebaseUserClient.updateByEmail(email, userUpdateRequest);
    }
}
