package com.example.identityservice.client;

import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.response.auth.UserInfoResponse;
import com.example.identityservice.dto.response.user.UsersInfoResponse;
import com.example.identityservice.enums.Gender;
import com.example.identityservice.mapper.UserMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
            DocumentSnapshot documentSnapshot = firestore.collection("users").document(userRecord.getUid()).get().get();
            String gender = documentSnapshot.exists() && documentSnapshot.contains("gender")
                    ? documentSnapshot.getString("gender")
                    : "MALE";
            Date dateOfBirth = documentSnapshot.exists() && documentSnapshot.contains("dateOfBirth")
                    ? documentSnapshot.getDate("dateOfBirth")
                    : null;
            UserInfoResponse res = UserMapper.INSTANCE.fromRecordToResponse(userRecord);
            res.setDateOfBirth(dateOfBirth);
            res.setGender(Gender.valueOf(gender));
            return res;

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
        try {
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
            // if (userUpdateRequest.getPassword() != null) {
            // request.setPassword(userUpdateRequest.getPassword());
            // }

            // Update Firebase Auth user record
            UserRecord userRecord = firebaseAuth.updateUser(request);

            // Update additional data in Firestore (e.g., gender and date of birth)
            DocumentReference userDocRef = firestore.collection("users").document(uid);
            DocumentSnapshot documentSnapshot = userDocRef.get().get();

            // Extract gender
            String gender = "MALE"; // Default gender
            if (documentSnapshot.exists() && documentSnapshot.contains("gender")) {
                gender = documentSnapshot.getString("gender");
            }

            // Extract date of birth
            Date dateOfBirth = null; // Default date of birth
            if (documentSnapshot.exists() && documentSnapshot.contains("dateOfBirth")) {
                dateOfBirth = documentSnapshot.getDate("dateOfBirth");
            }

            if (userUpdateRequest.getGender() != null) {
                userDocRef.update("gender", userUpdateRequest.getGender().name());
            }
            if (userUpdateRequest.getDateOfBirth() != null) {
                userDocRef.update("dateOfBirth", userUpdateRequest.getDateOfBirth());
            }
            UserInfoResponse res = UserMapper.INSTANCE.fromRecordToResponse(userRecord);
            res.setDateOfBirth(dateOfBirth);
            res.setGender(Gender.valueOf(gender));
            return res;
        } catch (FirebaseAuthException | InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error updating user information: " + e.getMessage(), e);
        } 
    }

    public UsersInfoResponse getUsers(String pageToken, int maxResults) {
        try {
            ListUsersPage listUsersPage = firebaseAuth.listUsers(pageToken, maxResults);
            Iterable<ExportedUserRecord> iterable =  listUsersPage.getValues();
            List<UserInfoResponse> userInfoResponses = new ArrayList<>();
            iterable.forEach(userInfoResponse -> userInfoResponses.add(UserMapper.INSTANCE.fromRecordToResponse(userInfoResponse)));
            for (UserInfoResponse userInfoResponse : userInfoResponses) {
                DocumentReference userDocRef = firestore.collection("users").document(userInfoResponse.getUserId());
                DocumentSnapshot documentSnapshot = userDocRef.get().get();
                String gender = "MALE"; // Default gender
                if (documentSnapshot.exists() && documentSnapshot.contains("gender")) {
                    gender = documentSnapshot.getString("gender");
                }
                Date dateOfBirth = null; // Default date of birth
                if (documentSnapshot.exists() && documentSnapshot.contains("dateOfBirth")) {
                    dateOfBirth = documentSnapshot.getDate("dateOfBirth");
                }
                userInfoResponse.setGender(Gender.valueOf(gender));
                userInfoResponse.setDateOfBirth(dateOfBirth);
            }
            return UsersInfoResponse.builder()
            .data(userInfoResponses)
            .nextPageToken(listUsersPage.getNextPageToken())
            .build();
            
        } catch (FirebaseAuthException | InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error get users information: " + e.getMessage(), e);

        }

    }

    public UserInfoResponse disableUser(String email, boolean disable) {
        return updateByEmail(email, UserUpdateRequest.builder().disabled(disable).build());
    }
}
