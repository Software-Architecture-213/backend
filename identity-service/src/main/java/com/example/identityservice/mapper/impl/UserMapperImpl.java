package com.example.identityservice.mapper.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.identityservice.dto.response.auth.UserInfoResponse;
import com.example.identityservice.enums.Role;
import com.example.identityservice.mapper.UserMapper;
import com.google.firebase.auth.UserRecord;


public class UserMapperImpl implements UserMapper {
    public static final UserMapperImpl INSTANCE = new UserMapperImpl();

    @Override
    public UserInfoResponse fromRecordToResponse(UserRecord userRecord) {
        String role = userRecord.getCustomClaims().get("role") != null ? userRecord.getCustomClaims().get("role").toString() : "USER"; 
        return UserInfoResponse.builder()
                    .userId(userRecord.getUid())
                    .email(userRecord.getEmail())
                    .displayName(userRecord.getDisplayName())
                    .phoneNumber(userRecord.getPhoneNumber())
                    .photoUrl(userRecord.getPhotoUrl())
                    .role(Role.valueOf(role))
                    .isDisabled(userRecord.isDisabled())
                    .build();
    }

    @Override
    public List<UserInfoResponse> fromRecordsToResponses(List<UserRecord> userRecords) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromRecordsToResponses'");
    }

    
}
