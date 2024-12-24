package com.example.identityservice.dto.response.user;

import java.util.List;

import com.example.identityservice.dto.response.auth.UserInfoResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsersInfoResponse {
    int page;
    String nextPageToken;
    List<UserInfoResponse> data;

}
