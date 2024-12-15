package com.example.identityservice.client;

import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.response.auth.UserInfoResponse;
import com.example.identityservice.enums.Gender;
import com.example.identityservice.enums.Role;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FirebaseUserClient {

    private final Firestore firestore;
    private final FirebaseAuth firebaseAuth;

    public UserInfoResponse getUserInfo(String userId, String email) {
        try {
            UserRecord userRecord;
            if (userId != null) {
                userRecord = firebaseAuth.getUser(userId);
            } else if (email != null) {
                userRecord = firebaseAuth.getUserByEmail(email);
            } else {
                throw new IllegalArgumentException("Either userId or email must be provided.");
            }
            String role = userRecord.getCustomClaims().get("role").toString();
            DocumentSnapshot documentSnapshot = firestore.collection("users").document(userRecord.getUid()).get().get();
            String gender = documentSnapshot.exists() && documentSnapshot.contains("gender")
                    ? documentSnapshot.getString("gender") : "MALE";
            Date dateOfBirth = documentSnapshot.exists() && documentSnapshot.contains("dateOfBirth")
                    ? documentSnapshot.getDate("dateOfBirth") : null;
            return  UserInfoResponse.builder().userId(userRecord.getUid())
                    .email(userRecord.getEmail())
                    .dateOfBirth(dateOfBirth)
                    .gender(Gender.valueOf(gender))
                    .displayName(userRecord.getDisplayName())
                    .phoneNumber(userRecord.getPhoneNumber())
                    .photoUrl(userRecord.getPhotoUrl())
                    .isDisabled(userRecord.isDisabled())
                    .role(Role.valueOf(role))
                    .build();

        } catch (FirebaseAuthException | InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error retrieving user information: " + e.getMessage(), e);
        }
    }

    public UserInfoResponse updateByEmail(@NonNull String email, @NonNull UserUpdateRequest userUpdateRequest) {
        try {
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);
            return updateByUid(userRecord.getUid(), userUpdateRequest);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error finding user by email: " + e.getMessage(), e);
        }
    }

    public UserInfoResponse updateByUid(@NonNull String uid, @NonNull UserUpdateRequest userUpdateRequest) {
        final var request = new UserRecord.UpdateRequest(uid);
        if (userUpdateRequest.getEmail() != null) {
            request.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getDisplayName() != null) {
            request.setDisplayName(userUpdateRequest.getDisplayName());
        }
        if (userUpdateRequest.getPhoneNumber() != null) {
            request.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        }
        if (userUpdateRequest.getPhotoUrl() != null && !userUpdateRequest.getPhotoUrl().isBlank()) {
            request.setPhotoUrl(userUpdateRequest.getPhotoUrl());
        }
        if (userUpdateRequest.getDisabled() != null) {
            request.setDisabled(userUpdateRequest.getDisabled());
        }
        /* Move to another service for change password */
//        if (userUpdateRequest.getPassword() != null) {
//            request.setPassword(userUpdateRequest.getPassword());
//        }
        try {
            // Update Firebase Auth user record
            UserRecord userRecord = firebaseAuth.updateUser(request);

            // Update additional data in Firestore (e.g., gender and date of birth)
            DocumentReference userDocRef = firestore.collection("users").document(uid);

            if (userUpdateRequest.getGender() != null) {
                userDocRef.update("gender", userUpdateRequest.getGender().name());
            }
            if (userUpdateRequest.getDateOfBirth() != null) {
                userDocRef.update("dateOfBirth", userUpdateRequest.getDateOfBirth());
            }
            // Return updated user information
            return UserInfoResponse.builder()
                    .userId(userRecord.getUid())
                    .email(userRecord.getEmail())
                    .dateOfBirth(userUpdateRequest.getDateOfBirth())
                    .gender(userUpdateRequest.getGender())
                    .displayName(userRecord.getDisplayName())
                    .phoneNumber(userRecord.getPhoneNumber())
                    .photoUrl(userRecord.getPhotoUrl())
                    .role(Role.valueOf(userRecord.getCustomClaims().get("role").toString()))
                    .isDisabled(userRecord.isDisabled())
                    .build();
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error updating user information: " + e.getMessage(), e);
        }
    }
}
